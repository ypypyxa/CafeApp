package com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.screen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.google.gson.Gson
import com.pivnoydevelopment.cafeapp.core.ui.components.CustomTopAppBar
import com.pivnoydevelopment.cafeapp.core.ui.components.DoubleLines
import com.pivnoydevelopment.cafeapp.core.ui.theme.EspressoDepth
import com.pivnoydevelopment.cafeapp.core.ui.theme.VanillaCream
import com.pivnoydevelopment.cafeapp.core.ui.theme.White
import com.pivnoydevelopment.cafeapp.features.locations.util.calculateDistance
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.components.CoffeeItemCard
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.components.LogoutDialog
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.components.PermissionDialog
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.event.CoffeeListEvent
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.viewmodel.CoffeeListViewModel
import com.pivnoydevelopment.cafeapp.navigation.CoffeeMap
import com.pivnoydevelopment.cafeapp.navigation.Login
import com.pivnoydevelopment.cafeapp.navigation.Menu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeListScreen(
    navController: NavController,
    viewModel: CoffeeListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            viewModel.onEvent(CoffeeListEvent.OnPermissionResult(granted))
            if (granted) {
                viewModel.fetchLastLocation()
            }
        }
    )

    LaunchedEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && state.locations.isEmpty()) {
                val granted = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                if (granted) {
                    viewModel.fetchLastLocation()
                } else if (!state.showPermissionDialog) {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
    }

    if (state.showPermissionDialog) {
        PermissionDialog(
            onOpenSettings = {
                viewModel.onEvent(CoffeeListEvent.OpenSettings)
                context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                })
            },
            onDismiss = { viewModel.onEvent(CoffeeListEvent.DismissPermissionDialog) }
        )
    }

    if (state.showLogoutDialog) {
        LogoutDialog(
            onConfirm = {
                viewModel.onEvent(CoffeeListEvent.Logout)
                navController.navigate(Login) {
                    popUpTo(Login) { inclusive = true }
                }
            },
            onDismiss = { viewModel.onEvent(CoffeeListEvent.DismissLogoutDialog) }
        )
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Ближайшие кофейни",
                onBackClick = { viewModel.onEvent(CoffeeListEvent.LogoutDialog) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .background(White)
        ) {
            DoubleLines()

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.errorMessage != null) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.errorMessage ?: "Ошибка", color = Color.Red)
                }
            } else if (state.locations.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    items(state.locations.size) { index ->
                        val location = state.locations[index]
                        CoffeeItemCard(
                            title = location.name,
                            distance = calculateDistance(
                                state.userLatitude ?: 0.0,
                                state.userLongitude ?: 0.0,
                                location.point.latitude,
                                location.point.longitude
                            ),
                            onClick = {
                                navController.navigate(Menu(location.id, location.name))
                            }
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 11.dp,
                            bottom = 11.dp
                        )
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EspressoDepth,
                        contentColor = VanillaCream,
                        disabledContainerColor = EspressoDepth.copy(alpha = 0.5f),
                        disabledContentColor = VanillaCream.copy(alpha = 0.5f)
                    ),
                    onClick = {
                        val locationsJson = Gson().toJson(state.locations)
                        navController.navigate(CoffeeMap(locationsJson))
                    }
                ) {
                    Text(
                        text = "На карту",
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700)
                    )
                }

                Spacer(
                    modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                )
            }
        }
    }
}
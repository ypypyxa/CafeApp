package com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.screen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.ui.components.DoubleLines
import com.pivnoydevelopment.cafeapp.core.ui.theme.IvoryWhisper
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieShade
import com.pivnoydevelopment.cafeapp.core.ui.theme.White
import com.pivnoydevelopment.cafeapp.features.locations.calculateDistance
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.components.CoffeeItem
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.event.CoffeeListEvent
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.viewmodel.CoffeeListViewModel
import com.pivnoydevelopment.cafeapp.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeListScreen(
    viewModel: CoffeeListViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val fusedLocationClient = remember(context) {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            viewModel.onEvent(CoffeeListEvent.OnPermissionResult(granted))
            if (granted) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        viewModel.onEvent(
                            CoffeeListEvent.UpdateUserLocation(
                                latitude = it.latitude,
                                longitude = it.longitude
                            )
                        )
                        viewModel.onEvent(CoffeeListEvent.LoadLocations)
                    }
                }
            }
        }
    )

    LaunchedEffect(lifecycleOwner) {
        if (!state.locations.isNotEmpty()) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    val granted = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

                    if (granted) {
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            location?.let {
                                viewModel.onEvent(
                                    CoffeeListEvent.UpdateUserLocation(it.latitude, it.longitude)
                                )
                                viewModel.onEvent(CoffeeListEvent.LoadLocations)
                            }
                        }
                    } else if (!state.showPermissionDialog) {
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
        }
    }

    if (state.showPermissionDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Доступ к геолокации") },
            text = { Text("Чтобы показать ближайшие кофейни, включите доступ к местоположению в настройках.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.onEvent(CoffeeListEvent.OpenSettings)
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    }
                ) {
                    Text("Открыть настройки")
                }
            },
            dismissButton = {
                Button(
                    onClick = { viewModel.onEvent(CoffeeListEvent.DismissPermissionDialog) }
                ) {
                    Text("Продолжить")
                }
            }
        )
    }

    if (state.showLogoutDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Выход из аккаунта") },
            text = { Text("Вы действительно хотите выйти из аккаунта?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.onEvent(CoffeeListEvent.Logout)
                        navController.navigate(Routes.Login.route) {
                            popUpTo(Routes.Login.route) { inclusive = true }
                        }
                    }
                ) {
                    Text("Да")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.onEvent(CoffeeListEvent.DismissLogoutDialog)
                    }
                ) {
                    Text("Нет")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = IvoryWhisper,
                ),
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 45.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight(700),
                        fontSize = 18.sp,
                        color = ToffieShade,
                        text = "Ближайшие кофейни")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(CoffeeListEvent.LogoutDialog)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Назад",
                            tint = ToffieShade
                        )
                    }
                }
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
                Text("Загрузка...", modifier = Modifier.padding(16.dp))
            } else if (state.errorMessage != null) {
                Text(state.errorMessage ?: "Ошибка", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.locations.size) { index ->
                        val location = state.locations[index]
                        CoffeeItem(
                            title = location.name,
                            distance = calculateDistance(
                                state.userLatitude ?: 0.0,
                                state.userLongitude ?: 0.0,
                                location.point.latitude,
                                location.point.longitude
                            )
                        )
                    }
                }
            }
        }
    }
}
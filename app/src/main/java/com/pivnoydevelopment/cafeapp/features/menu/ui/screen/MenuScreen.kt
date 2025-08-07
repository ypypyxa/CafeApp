package com.pivnoydevelopment.cafeapp.features.menu.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.ui.components.CustomTopAppBar
import com.pivnoydevelopment.cafeapp.core.ui.components.DoubleLines
import com.pivnoydevelopment.cafeapp.core.ui.theme.EspressoDepth
import com.pivnoydevelopment.cafeapp.core.ui.theme.VanillaCream
import com.pivnoydevelopment.cafeapp.core.ui.theme.White
import com.pivnoydevelopment.cafeapp.features.menu.ui.components.MenuItemCard
import com.pivnoydevelopment.cafeapp.features.menu.ui.event.MenuEvent
import com.pivnoydevelopment.cafeapp.features.menu.ui.viewmodel.MenuViewModel
import com.pivnoydevelopment.cafeapp.navigation.Cart

@Composable
fun MenuScreen(
    navController: NavController,
    id: Int,
    name: String,
    viewModel: MenuViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(MenuEvent.LoadMenu(id))
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.menu),
                onBackClick = { navController.popBackStack() }
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

            when {
                state.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                state.errorMessage != null -> {
                    val errorMessage =
                        state.errorMessage ?: stringResource(R.string.unknown_error)
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = errorMessage, color = Color.Red)
                    }
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        items(state.menuItems.size) { index ->
                            val item = state.menuItems[index]
                            MenuItemCard(
                                title = item.name,
                                price = item.price,
                                count = item.count,
                                imageUrl = item.imageUrl,
                                onIncrease = { viewModel.onEvent(MenuEvent.AddItem(id, name, item)) },
                                onDecrease = { viewModel.onEvent(MenuEvent.RemoveItem(id, item)) }
                            )
                        }
                    }
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
                enabled = state.menuItems.any { it.count > 0 },
                onClick = { navController.navigate(Cart(id, name)) }
            ) {
                Text(
                    text = stringResource(R.string.to_cart),
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
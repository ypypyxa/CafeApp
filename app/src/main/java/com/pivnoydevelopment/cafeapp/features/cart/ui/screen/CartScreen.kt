package com.pivnoydevelopment.cafeapp.features.cart.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.ui.components.CustomTopAppBar
import com.pivnoydevelopment.cafeapp.core.ui.components.DoubleLines
import com.pivnoydevelopment.cafeapp.core.ui.theme.EspressoDepth
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieShade
import com.pivnoydevelopment.cafeapp.core.ui.theme.VanillaCream
import com.pivnoydevelopment.cafeapp.core.ui.theme.White
import com.pivnoydevelopment.cafeapp.features.cart.ui.components.CartItemCard
import com.pivnoydevelopment.cafeapp.features.cart.ui.event.CartEvent
import com.pivnoydevelopment.cafeapp.features.cart.ui.viewmodel.CartViewModel

@Composable
fun CartScreen(
    navController: NavController,
    id: Int,
    name: String,
    viewModel: CartViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(CartEvent.LoadCart(id))
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.cart),
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 10.dp,
                        bottom = 5.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(500),
                    fontSize = 24.sp,
                    color = ToffieShade
                )
            }

            when {
                state.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        items(state.menuItems.size) { index ->
                            val item = state.menuItems[index]
                            CartItemCard(
                                title = item.name,
                                price = item.price,
                                count = item.count,
                                onIncrease = { viewModel.onEvent(CartEvent.AddItem(id, name, item)) },
                                onDecrease = { viewModel.onEvent(CartEvent.RemoveItem(id, item)) }
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 10.dp,
                                bottom = 10.dp
                            ),
                        text = stringResource(R.string.order_price),
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700),
                        color = ToffieShade
                    )
                    val priceSuffix = stringResource(R.string.price_suffix)
                    Text(
                        modifier = Modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 10.dp,
                                bottom = 10.dp
                            ),
                        text = "${state.totalPrice} $priceSuffix",
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700),
                        color = ToffieShade
                    )
                }

                if (state.isSubmiting) {
                    Text(
                        text = stringResource(R.string.order_submitted),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight(500),
                        fontSize = 24.sp,
                        color = ToffieShade
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
                enabled = state.menuItems.any { it.count > 0 },
                onClick = { viewModel.onEvent(CartEvent.SubmitOrder(id)) }
            ) {
                Text(
                    text = stringResource(R.string.submit_order),
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
package com.pivnoydevelopment.cafeapp.features.cart.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pivnoydevelopment.cafeapp.core.ui.components.CustomTopAppBar
import com.pivnoydevelopment.cafeapp.core.ui.components.DoubleLines
import com.pivnoydevelopment.cafeapp.core.ui.theme.EspressoDepth
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieShade
import com.pivnoydevelopment.cafeapp.core.ui.theme.VanillaCream
import com.pivnoydevelopment.cafeapp.core.ui.theme.White
import com.pivnoydevelopment.cafeapp.features.cart.ui.components.CartItemCard

@Preview(showBackground = true)
@Composable
fun CartScreen(
    navController: NavController = rememberNavController(),
) {

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Ваш Заказ",
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                items(3) { index ->
                    CartItemCard()
                }
            }

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
                    text = "Итого:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700),
                    color = ToffieShade
                )
                Text(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 10.dp,
                            bottom = 10.dp
                        ),
                    text = "750 руб",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700),
                    color = ToffieShade
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Время ожидания заказа\n" +
                            "15 минут!\n" +
                            "Спасибо, что выбрали нас!",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(500),
                    fontSize = 24.sp,
                    color = ToffieShade
                )
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
//                enabled = state.menuItems.any { it.count > 0 },
                onClick = { /* TODO сообщение об оплате */ }
            ) {
                Text(
                    text = "Оплатить",
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
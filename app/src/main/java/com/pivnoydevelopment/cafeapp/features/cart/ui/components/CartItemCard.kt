package com.pivnoydevelopment.cafeapp.features.cart.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieGlaze
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieShade
import com.pivnoydevelopment.cafeapp.core.ui.theme.VanillaCream

@Preview
@Composable
fun CartItemCard(
    title: String = "Капучино",
    price: Int = 250,
    count: Int = 1,
    onIncrease: () -> Unit = {},
    onDecrease: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 3.dp,
                start =  6.dp,
                end = 6.dp,
                bottom = 3.dp
            ),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(VanillaCream)
                .padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(
                        top = 14.dp,
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 3.dp
                    ),
                    text = title,
                    color = ToffieShade,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(700)
                )
                val priceSuffix = stringResource(R.string.price_suffix)
                Text(
                    modifier = Modifier.padding(
                        top = 3.dp,
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    ),
                    text = "$price $priceSuffix",
                    color = ToffieGlaze,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    modifier = Modifier
                        .size(24.dp),
                    onClick = onDecrease
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = stringResource(R.string.decrease),
                        tint = ToffieShade
                    )
                }

                Text(
                    text = count.toString(),
                    modifier = Modifier.padding(horizontal = 9.dp),
                    color = ToffieShade,
                    fontWeight = FontWeight(400),
                    fontSize = 14.sp
                )

                IconButton(
                    modifier = Modifier
                        .size(24.dp),
                    onClick = onIncrease
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = stringResource(R.string.increase),
                        tint = ToffieShade
                    )
                }
            }
        }
    }
}
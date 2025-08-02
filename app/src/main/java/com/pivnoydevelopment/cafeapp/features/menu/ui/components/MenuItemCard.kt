package com.pivnoydevelopment.cafeapp.features.menu.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.ui.theme.IvoryWhisper
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieGlaze
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieShade
import com.pivnoydevelopment.cafeapp.core.ui.theme.VanillaCream

@Preview(showBackground = false)
@Composable
fun MenuItemCard(
    modifier: Modifier = Modifier,
    title: String = "Cappuccino",
    price: Int = 250,
    count: Int = 0,
    imageUrl: String = "",
    onIncrease: () -> Unit = {},
    onDecrease: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(170.dp)
            .wrapContentHeight()
            .padding(5.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(IvoryWhisper),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(6f / 5f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    placeholder = painterResource(id = R.drawable.ic_coffee_placeholder),
                    error = painterResource(id = R.drawable.ic_coffee_placeholder),
                    model = imageUrl,
                    contentDescription = title,
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                modifier = Modifier
                    .padding(
                        top = 10.dp,
                        start = 11.dp,
                        end = 11.dp
                    ),
                text = title,
                color = ToffieGlaze,
                fontWeight = FontWeight(500),
                fontSize = 18.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 12.dp,
                        start = 12.dp,
                        end = 6.dp,
                        bottom = 11.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${price} руб",
                    color = ToffieShade,
                    fontWeight = FontWeight(700),
                    fontSize = 14.sp
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = onDecrease
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_minus),
                            contentDescription = "Decrease",
                            tint = VanillaCream
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
                        modifier = Modifier.size(24.dp),
                        onClick = onIncrease
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = "Increase",
                            tint = VanillaCream
                        )
                    }
                }
            }
        }
    }
}
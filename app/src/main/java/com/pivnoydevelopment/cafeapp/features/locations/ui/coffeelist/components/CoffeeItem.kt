package com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieGlaze
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieShade
import com.pivnoydevelopment.cafeapp.core.ui.theme.VanillaCream
import java.util.Locale

@Preview(showBackground = false)
@Composable
fun CoffeeItem(
    title: String = "BEDOEV COFFEE",
    distance: Float? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 3.dp,
                start = 10.dp,
                end = 10.dp,
                bottom = 3.dp
            )
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(5.dp),
                clip = false
            )
            .clip(RoundedCornerShape(5.dp))
            .background(VanillaCream)
    ) {
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

        if (distance != null) {
            Text(
                modifier = Modifier.padding(
                    top = 3.dp,
                    start = 10.dp,
                    end = 10.dp,
                    bottom = 10.dp
                ),
                text = if (distance < 1) {
                    "${(distance * 1000).toInt()}м от Вас"
                } else {
                    String.format(Locale.getDefault(), "%.0fкм от Вас", distance)
                },
                color = ToffieGlaze,
                fontSize = 14.sp,
                fontWeight = FontWeight(400)
            )
        } else {
            Text(
                modifier = Modifier.padding(
                    top = 3.dp,
                    start = 10.dp,
                    end = 10.dp,
                    bottom = 10.dp
                ),
                text = "Расстояние неизвестно",
                color = ToffieGlaze,
                fontSize = 14.sp,
                fontWeight = FontWeight(400)
            )
        }

    }
}
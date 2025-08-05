package com.pivnoydevelopment.cafeapp.features.locations.ui.coffeemap.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pivnoydevelopment.cafeapp.features.locations.domain.model.Location

@Preview(showBackground = true)
@Composable
fun CoffeeMapScreen(
    navController: NavController = rememberNavController(),
    locations: List<Location> = emptyList()
) {
    Text(text = locations.toString())
}
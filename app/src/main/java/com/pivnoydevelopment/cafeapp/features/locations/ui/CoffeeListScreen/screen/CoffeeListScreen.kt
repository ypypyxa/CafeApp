package com.pivnoydevelopment.cafeapp.features.locations.ui.CoffeeListScreen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.ui.components.DoubleLines
import com.pivnoydevelopment.cafeapp.core.ui.theme.IvoryWhisper
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieShade
import com.pivnoydevelopment.cafeapp.core.ui.theme.White
import com.pivnoydevelopment.cafeapp.features.locations.ui.CoffeeListScreen.components.CoffeeItem

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CoffeeListScreen() {
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
                    IconButton(onClick = { /* TODO Видимо логаут */ }) {
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
        Column(modifier = Modifier
            .padding( top = innerPadding.calculateTopPadding())
            .background(White)
        ) {
            DoubleLines()

            Column(
                modifier = Modifier
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    userScrollEnabled = true
                ) {
                    items(15) { index ->
                        CoffeeItem(
                            title = "Кофе №${index + 1}",
                            distance = 1
                        )
                    }
                }
            }
        }
    }
}
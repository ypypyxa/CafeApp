package com.pivnoydevelopment.cafeapp.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.ui.theme.IvoryWhisper
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieShade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    onBackClick: (() -> Unit)? = null
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = IvoryWhisper),
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = if (onBackClick != null) 45.dp else 0.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(700),
                fontSize = 18.sp,
                color = ToffieShade,
                text = title
            )
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = stringResource(R.string.back),
                        tint = ToffieShade
                    )
                }
            }
        }
    )
}
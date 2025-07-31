package com.pivnoydevelopment.cafeapp.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pivnoydevelopment.cafeapp.core.ui.theme.CafeAppTheme
import com.pivnoydevelopment.cafeapp.core.ui.theme.setupStatusBarIconsColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupStatusBarIconsColor()
        setContent {
            CafeAppTheme {
                MainScreen()
            }
        }
    }
}

//WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
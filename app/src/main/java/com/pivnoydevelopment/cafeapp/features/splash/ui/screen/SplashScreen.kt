package com.pivnoydevelopment.cafeapp.features.splash.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.ui.theme.White
import com.pivnoydevelopment.cafeapp.features.splash.ui.state.Authorized
import com.pivnoydevelopment.cafeapp.features.splash.ui.event.CheckAuth
import com.pivnoydevelopment.cafeapp.features.splash.ui.state.Loading
import com.pivnoydevelopment.cafeapp.features.splash.ui.viewmodel.SplashViewModel
import com.pivnoydevelopment.cafeapp.features.splash.ui.state.Unauthorized
import com.pivnoydevelopment.cafeapp.navigation.CoffeeList
import com.pivnoydevelopment.cafeapp.navigation.Login
import com.pivnoydevelopment.cafeapp.navigation.Splash

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1
    )

    LaunchedEffect(Unit) {
        viewModel.onEvent(CheckAuth)
    }

    LaunchedEffect(progress, state) {
        if (progress == 1f) {
            when (state) {
                is Authorized -> {
                    navController.navigate(CoffeeList) {
                        popUpTo(Splash) { inclusive = true }
                    }
                }
                is Unauthorized -> {
                    navController.navigate(Login) {
                        popUpTo(Splash) { inclusive = true }
                    }
                }
                Loading -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
        )
    }
}
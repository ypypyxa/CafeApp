package com.pivnoydevelopment.cafeapp.features.splash.ui.state

sealed interface SplashState
object Loading : SplashState
object Authorized : SplashState
object Unauthorized : SplashState
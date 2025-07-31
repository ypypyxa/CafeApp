package com.pivnoydevelopment.cafeapp.features.auth.ui.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pivnoydevelopment.cafeapp.core.ui.components.CustomTextField
import com.pivnoydevelopment.cafeapp.core.ui.components.DoubleLines
import com.pivnoydevelopment.cafeapp.core.ui.theme.EmeraldSprout
import com.pivnoydevelopment.cafeapp.core.ui.theme.EspressoDepth
import com.pivnoydevelopment.cafeapp.core.ui.theme.IvoryWhisper
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieShade
import com.pivnoydevelopment.cafeapp.core.ui.theme.VanillaCream
import com.pivnoydevelopment.cafeapp.core.ui.theme.White
import com.pivnoydevelopment.cafeapp.features.auth.ui.login.event.LoginEvent
import com.pivnoydevelopment.cafeapp.features.auth.ui.login.viewmodel.LoginViewModel
import com.pivnoydevelopment.cafeapp.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navigateToCoffeeList) {
        if (state.navigateToCoffeeList) {
            navController.navigate(Routes.CoffeeList.route) {
                popUpTo(Routes.Login.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = IvoryWhisper,
                ),
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight(700),
                        fontSize = 18.sp,
                        color = ToffieShade,
                        text = "Вход")
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
                CustomTextField(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    value = state.login,
                    onValueChange = { viewModel.onEvent(LoginEvent.LoginChanged(it))},
                    label = "E-mail",
                    errorMessage = state.loginError,
                    isEmail = true
                )

                CustomTextField(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 24.dp
                        ),
                    value = state.password,
                    onValueChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
                    label = "Пароль",
                    errorMessage = state.passwordError,
                    isPassword = true
                )

                Button(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 30.dp
                        )
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EspressoDepth,
                        contentColor = VanillaCream,
                        disabledContainerColor = EspressoDepth.copy(alpha = 0.5f),
                        disabledContentColor = VanillaCream.copy(alpha = 0.5f)
                    ),
                    enabled = state.isButtonEnabled,
                    onClick = { viewModel.onEvent(LoginEvent.Submit) }
                ) {
                    Text(
                        text = if (state.isLoading) "Загрузка..." else "Войти",
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .clickable{
                                navController.navigate(Routes.Register.route)
                            },
                        textAlign = TextAlign.Center,
                        color = EmeraldSprout,
                        fontWeight = FontWeight(700),
                        text = "Нет аккаунта? Регистрация",
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}
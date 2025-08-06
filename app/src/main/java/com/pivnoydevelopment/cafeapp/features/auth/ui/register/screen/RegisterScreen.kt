package com.pivnoydevelopment.cafeapp.features.auth.ui.register.screen

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.ui.components.CustomTextField
import com.pivnoydevelopment.cafeapp.core.ui.components.CustomTopAppBar
import com.pivnoydevelopment.cafeapp.core.ui.components.DoubleLines
import com.pivnoydevelopment.cafeapp.core.ui.theme.EspressoDepth
import com.pivnoydevelopment.cafeapp.core.ui.theme.VanillaCream
import com.pivnoydevelopment.cafeapp.core.ui.theme.White
import com.pivnoydevelopment.cafeapp.features.auth.ui.register.event.RegisterEvent
import com.pivnoydevelopment.cafeapp.features.auth.ui.register.viewmodel.RegisterViewModel
import com.pivnoydevelopment.cafeapp.navigation.CoffeeList
import com.pivnoydevelopment.cafeapp.navigation.Register

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    if (state.navigateToCoffeeList) {
        navController.navigate(CoffeeList) {
            popUpTo(Register) { inclusive = true }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.register),
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
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
                    modifier = Modifier.padding(horizontal = 16.dp),
                    value = state.login,
                    onValueChange = { viewModel.onEvent(RegisterEvent.LoginChanged(it)) },
                    label = stringResource(R.string.email),
                    errorMessage = state.loginError,
                    isEmail = true
                )

                CustomTextField(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp),
                    value = state.password,
                    onValueChange = { viewModel.onEvent(RegisterEvent.PasswordChanged(it)) },
                    label = stringResource(R.string.password),
                    isPassword = true,
                    errorMessage = state.passwordError
                )

                CustomTextField(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp),
                    value = state.confirmPassword,
                    onValueChange = { viewModel.onEvent(RegisterEvent.ConfirmPasswordChanged(it)) },
                    label = stringResource(R.string.confirm_password),
                    isPassword = true,
                    errorMessage = state.confirmPasswordError
                )

                Button(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 30.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EspressoDepth,
                        contentColor = VanillaCream,
                        disabledContainerColor = EspressoDepth.copy(alpha = 0.5f),
                        disabledContentColor = VanillaCream.copy(alpha = 0.5f)
                    ),
                    enabled = state.isButtonEnabled,
                    onClick = { viewModel.onEvent(RegisterEvent.Submit) }
                ) {
                    Text(
                        text = if (state.isLoading)
                            stringResource(R.string.loading)
                        else
                            stringResource(R.string.register),
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700)
                    )
                }
            }
        }
    }
}
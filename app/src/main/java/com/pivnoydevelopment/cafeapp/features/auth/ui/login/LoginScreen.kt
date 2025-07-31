package com.pivnoydevelopment.cafeapp.features.auth.ui.login

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pivnoydevelopment.cafeapp.core.ui.components.CustomTextField
import com.pivnoydevelopment.cafeapp.core.ui.components.DoubleLines
import com.pivnoydevelopment.cafeapp.core.ui.theme.EmeraldSprout
import com.pivnoydevelopment.cafeapp.core.ui.theme.EspressoDepth
import com.pivnoydevelopment.cafeapp.core.ui.theme.IvoryWhisper
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieShade
import com.pivnoydevelopment.cafeapp.core.ui.theme.VanillaCream
import com.pivnoydevelopment.cafeapp.core.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    var login by remember { mutableStateOf("") }
    val emailPattern = android.util.Patterns.EMAIL_ADDRESS
    val loginError = if (login.isNotEmpty() && !emailPattern.matcher(login).matches())
        "Введите корректный e-mail" else null

    var password by remember { mutableStateOf("") }

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
                    value = login,
                    onValueChange = { login = it },
                    label = "E-mail",
                    errorMessage = loginError
                )

                CustomTextField(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 24.dp
                        ),
                    value = password,
                    onValueChange = { password = it },
                    label = "Пароль",
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
                        contentColor = VanillaCream
                    ),
                    onClick = {
                        //TODO Авторизация
                    }
                ) {
                    Text(
                        text = "Войти",
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
                                //TODO Переход на экран регистрации
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
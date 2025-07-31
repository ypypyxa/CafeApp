package com.pivnoydevelopment.cafeapp.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieGlaze
import com.pivnoydevelopment.cafeapp.core.ui.theme.ToffieShade

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isEmail: Boolean = false,
    isPassword: Boolean = false,
    errorMessage: String? = null,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(bottom = 7.51.dp),
            text = label,
            color = ToffieShade,
            fontWeight = FontWeight(400),
            fontSize = 15.sp,
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = when {
                    isPassword -> KeyboardType.Password
                    isEmail -> KeyboardType.Email
                    else -> KeyboardType.Text
                },
                imeAction = ImeAction.Next
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            textStyle = LocalTextStyle.current.copy(
                color = if (errorMessage == null) ToffieGlaze else Color.Red,
                fontWeight = FontWeight(400),
                fontSize = 18.sp
            ),
            cursorBrush = SolidColor(if (errorMessage == null) ToffieGlaze else Color.Red),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.Transparent, shape = RoundedCornerShape(24.5.dp))
                .border(2.dp, if (errorMessage == null) ToffieShade else Color.Red, RoundedCornerShape(50.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp
            )
        }
    }
}
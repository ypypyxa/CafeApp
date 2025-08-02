package com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.components

import androidx.compose.runtime.Composable
import com.pivnoydevelopment.cafeapp.core.ui.components.CustomDialog

@Composable
fun PermissionDialog(
    onOpenSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    CustomDialog(
        title = "Доступ к геолокации",
        text = "Чтобы показать ближайшие кофейни, включите доступ к местоположению в настройках.",
        confirmText = "Открыть настройки",
        onConfirm = onOpenSettings,
        dismissText = "Продолжить",
        onDismiss = onDismiss
    )
}

@Composable
fun LogoutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    CustomDialog(
        title = "Выход из аккаунта",
        text = "Вы действительно хотите выйти из аккаунта?",
        confirmText = "Да",
        onConfirm = onConfirm,
        dismissText = "Нет",
        onDismiss = onDismiss
    )
}
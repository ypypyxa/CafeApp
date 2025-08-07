package com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.ui.components.CustomDialog

@Composable
fun PermissionDialog(
    onOpenSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    CustomDialog(
        title = stringResource(R.string.geo_permission_title),
        text = stringResource(R.string.geo_permission_text),
        confirmText = stringResource(R.string.open_settings),
        onConfirm = onOpenSettings,
        dismissText = stringResource(R.string.cont),
        onDismiss = onDismiss
    )
}

@Composable
fun LogoutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    CustomDialog(
        title = stringResource(R.string.log_out_title),
        text = stringResource(R.string.log_out_text),
        confirmText = stringResource(R.string.yes),
        onConfirm = onConfirm,
        dismissText = stringResource(R.string.no),
        onDismiss = onDismiss
    )
}
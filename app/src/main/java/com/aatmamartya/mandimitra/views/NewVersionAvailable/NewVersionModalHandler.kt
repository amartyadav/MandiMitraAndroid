package com.aatmamartya.mandimitra.views.NewVersionAvailable

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun NewVersionModalHandler(
    currentAppVersion: String,
    latestAppVersion: String,
    oUpdateClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val showNewVersionModal = remember {
        mutableStateOf(currentAppVersion != latestAppVersion)
    }

    Log.d("NewVersionModalHandler", "currentAppVersion: $currentAppVersion")
    Log.d("NewVersionModalHandler", "latestAppVersion: $latestAppVersion")
    Log.d("NewVersionModalHandler", "showNewVersionModal: ${showNewVersionModal.value}")

    if (showNewVersionModal.value) {
        NewVersionModal(
            message = "A new version of Mandi Mitra is available 😎\n" +
                    "Update your app now for exciting new features and improvements 🔥 ",
            onUpdateClick = {
                oUpdateClick()
                showNewVersionModal.value = false
            },
            onDismissClick = {
                showNewVersionModal.value = false
            },
        )
    }

    content()
}
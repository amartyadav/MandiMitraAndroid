package com.aatmamartya.mandimitra.views.NewVersionAvailable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.aatmamartya.mandimitra.MandiMitraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewVersionModal(
    message: String,
    onUpdateClick: () -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier,
//    icon: ImageVector
) {
    MandiMitraTheme {
        AlertDialog(
//        icon = { Icon(icon, contentDescription = "Update Available Icon") },
            title = {
                Text(
                    text = "New Update Available",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center // Center-align the title text
                )
            },
            text = {
                Text(
                    text = message,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center // Center-align the title text
                )
            },
            dismissButton = {
                TextButton(onClick = { onDismissClick() }) {
                    Text(text = "Update Later")
                }
            },
            onDismissRequest = { onDismissClick() },
            confirmButton = {
                TextButton(onClick = { onUpdateClick() }) {
                    Text(text = "Update Now")
                }
            }
        )
    }

}
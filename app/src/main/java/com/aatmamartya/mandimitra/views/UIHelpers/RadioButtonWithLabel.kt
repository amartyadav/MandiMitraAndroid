package com.aatmamartya.mandimitra.views.UIHelpers

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonWithLabel(
    quantity: Int,
    isSelected: Boolean,
    onSelectionChanged: (Int) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 8.dp)
    ) {
        RadioButton(
            selected = isSelected,
            onClick = {
                onSelectionChanged(quantity)
                keyboardController?.hide()
            },
            modifier = Modifier.padding(start = 8.dp)
        )
        Text(
            text = stringResource(id = quantity),
            modifier = Modifier.padding(end = 8.dp),
            style = MaterialTheme.typography.bodyMedium
            // Assuming fontFamily is defined globally or passed as a parameter
        )
    }
}
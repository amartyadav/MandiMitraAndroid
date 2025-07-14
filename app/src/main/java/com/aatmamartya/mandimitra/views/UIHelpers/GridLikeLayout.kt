package com.aatmamartya.mandimitra.views.UIHelpers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GridLikeLayout(
    quantitySelections: List<Int>,
    buyingQuantityUnitSelected: Int,
    onSelectionChanged: (Int) -> Unit
) {
    BoxWithConstraints {
        // Dynamically adjust the number of columns based on screen width

        val columns = if (maxWidth > 350.dp) 3 else 2 // Adjust for large display sizes

        val rows = (quantitySelections.size + columns - 1) / columns

        Column(modifier = Modifier.padding(10.dp)) {
            for (row in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    for (column in 0 until columns) {
                        val index = row * columns + column
                        if (index < quantitySelections.size) {
                            val quantity = quantitySelections[index]
                            RadioButtonWithLabel(
                                quantity = quantity,
                                isSelected = quantity == buyingQuantityUnitSelected,
                                onSelectionChanged = onSelectionChanged
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f)) // Fill empty space if no more items
                        }
                    }
                }
            }
        }
    }
}
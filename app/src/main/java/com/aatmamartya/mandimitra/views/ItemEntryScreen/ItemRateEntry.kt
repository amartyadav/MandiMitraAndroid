package com.aatmamartya.mandimitra.views.ItemEntryScreen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aatmamartya.mandimitra.R
import com.aatmamartya.mandimitra.fontFamily


@Composable
fun ItemRateEntry(
    price: String,
    onPriceChange: (String) -> Unit,
    sellingQuantityUnitSelected: Int,
    onSellingQuantityUnitSelectedChange: (Int) -> Unit,
    quantityUnits: List<Int>,
    isLargeDisplay: Boolean,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?
) {
    // Implementation for the Item Rate Entry UI
    ElevatedCard(
        modifier = Modifier
            .padding(16.dp) // Add padding around the card
            .fillMaxWidth(), // Make the card take up the full width
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        if (!isLargeDisplay) {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Enter Item Rate",
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = fontFamily
                )
                Spacer(modifier = Modifier.weight(1f))

            }
        } else {
            Row {
                Text(
                    text = "Enter Item Rate",
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = fontFamily
                )
            }
        }


        val quantityUnits = listOf(R.string.paao, R.string.KG)


        BoxWithConstraints {
            val isLargeDisplay = maxWidth > 350.dp
            Row(modifier = Modifier.padding(16.dp)) {
                if (isLargeDisplay) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ruppee_icon_light),
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .width(100.dp),
                            value = price,
                            onValueChange = { newPrice -> onPriceChange(newPrice) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions { focusManager.clearFocus() },
                            label = {
                                Text("Price")
                            },
                            textStyle = TextStyle.Default.copy(
                                fontSize = 22.sp, fontFamily = fontFamily
                            )
                        )
                        // Price and Quantity input
                        Row(modifier = Modifier.padding(20.dp)) {

                            // Adjusted to a Row for a more compact layout
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 10.dp)
                            ) {
                                quantityUnits.forEach { unit ->
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        RadioButton(
                                            selected = sellingQuantityUnitSelected == unit,
                                            onClick = {
                                                onSellingQuantityUnitSelectedChange(unit)
                                                keyboardController?.hide()
                                            }
                                        )
                                        Text(
                                            text = stringResource(id = unit),
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontFamily = fontFamily
                                        )
                                    }

                                }
                            }
                        }
                    }

                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        )
                        {
                            // spacer to push the text to the right
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(R.drawable.ruppee_icon_light),
                                contentDescription = null,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            OutlinedTextField(
                                modifier = Modifier
                                    .width(100.dp),
                                value = price,
                                onValueChange = { newPrice -> onPriceChange(newPrice) },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done,
                                ),
                                keyboardActions = KeyboardActions { focusManager.clearFocus() },
                                label = {
                                    Text("Price")
                                },
                                textStyle = TextStyle.Default.copy(
                                    fontSize = 22.sp, fontFamily = fontFamily
                                )
                            )
                            Spacer(modifier = Modifier.weight(1f))

                        }

                        // Price and Quantity input
                        // Adjusted to a Row for a more compact layout
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 10.dp)
                        ) {
                            quantityUnits.forEach { unit ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = sellingQuantityUnitSelected == unit,
                                        onClick = {
                                            onSellingQuantityUnitSelectedChange(unit)
                                            keyboardController?.hide()
                                        }
                                    )
                                    Text(
                                        text = stringResource(id = unit),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontFamily = fontFamily
                                    )
                                }

                            }
                        }
                    }


                }
            }

        }

    }

}
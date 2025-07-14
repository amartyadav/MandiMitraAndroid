package com.aatmamartya.mandimitra.views.ItemEntryScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aatmamartya.mandimitra.R
import com.aatmamartya.mandimitra.ShoppingItem
import com.aatmamartya.mandimitra.fontFamily
import com.aatmamartya.mandimitra.views.UIHelpers.GridLikeLayout

// Todo: Implement the BuyingQuantitySelection composable function
@Composable
fun BuyingQuantitySelection(
    onMoreKGChange: (String) -> Unit,
    buyingQuantityUnitSelected: Int,
    onBuyingQuantityUnitSelectedChange: (Int) -> Unit,
    customQuantity: String,
    onCustomQuantityChange: (String) -> Unit,
    quantitySelections: List<Int>,
    keyboardController: SoftwareKeyboardController?,
    isLargeDisplay: Boolean,
    focusManager: FocusManager,
    price: String,
    sellingQuantityUnitSelected: Int,
    shoppingList: MutableList<ShoppingItem>,
    onPriceChange: (String) -> Unit,
    isAddItemButtonEnabled: Boolean
) {

    val context = LocalContext.current

    // Implementation for the Buying Quantity Selection UI
    // Buying Section
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
                    text = stringResource(id = R.string.you_want_to_buy),
                    modifier = Modifier.padding(
                        start = 20.dp,
                        top = 20.dp,
                        bottom = 10.dp
                    ),
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = fontFamily
                )
                Spacer(modifier = Modifier.weight(1f))

            }
        } else {
            Row {
                Text(
                    text = stringResource(id = R.string.you_want_to_buy),
                    modifier = Modifier.padding(
                        start = 20.dp,
                        top = 20.dp,
                        bottom = 10.dp
                    ),
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = fontFamily
                )
            }
        }



        Spacer(modifier = Modifier.weight(1f))
        // Grid layout for radio buttons
        GridLikeLayout(
            quantitySelections = quantitySelections,
            buyingQuantityUnitSelected = buyingQuantityUnitSelected,
            onSelectionChanged = { selection ->
                onBuyingQuantityUnitSelectedChange(selection)
            }
        )
        Spacer(modifier = Modifier.weight(1f))


        // Conditional rendering for custom quantity input
        if (buyingQuantityUnitSelected == R.string.more_kg) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.weight(1f)) // Spacer to push everything to center

                OutlinedTextField(
                    value = customQuantity,
                    onValueChange = { newValue -> onMoreKGChange(newValue) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions { focusManager.clearFocus() },
                    label = { Text("More KG") },
                    textStyle = TextStyle.Default.copy(
                        fontSize = 22.sp, fontFamily = fontFamily
                    ),
                    modifier = Modifier
                        .width(100.dp) // Set the width as 100.dp
                )
                Text(
                    text = "Kilos",
                    modifier = Modifier.padding(start = 8.dp, top = 10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 15.sp,
                    fontFamily = fontFamily
                )

                Spacer(modifier = Modifier.weight(1f)) // Spacer to ensure the TextField stays in center
            }
        }

        if (!isLargeDisplay) {
            Row(modifier = Modifier.padding(end = 20.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                // Add Item to list button
                val pricePerUnitText =
                    stringResource(id = sellingQuantityUnitSelected) // Convert resource ID to string
                val quantityText =
                    stringResource(id = buyingQuantityUnitSelected) // Convert resource ID to string
                Button(
                    onClick = {

                        val total = calculateItemTotal(
                            price,
                            sellingQuantityUnitSelected,
                            buyingQuantityUnitSelected,
                            customQuantity
                        )
                        shoppingList.add(
                            ShoppingItem(
                                "$price per $pricePerUnitText",
                                quantityText,
                                total
                            )
                        )
                        onPriceChange("")
                        onCustomQuantityChange("")
                        // Reset customQuantity after adding the item if needed
                        if (buyingQuantityUnitSelected == R.string.more_kg) {
                            onCustomQuantityChange(
                                "" // Reset if you're clearing the form upon adding
                            )
                        }

                        // toast("Item added to list")
                        Toast.makeText(
                            context,
                            "Item added to list",
                            Toast.LENGTH_SHORT
                        ).show()

                        keyboardController?.hide()
                    },
                    enabled = isAddItemButtonEnabled,
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_item),
                        fontFamily = fontFamily
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

            }
        } else {
            Row(modifier = Modifier.padding(end = 20.dp)) {
                Spacer(modifier = Modifier.weight(1f))

                // Add Item to list button
                val pricePerUnitText =
                    stringResource(id = sellingQuantityUnitSelected) // Convert resource ID to string
                val quantityText =
                    stringResource(id = buyingQuantityUnitSelected) // Convert resource ID to string
                Button(
                    onClick = {

                        val total = calculateItemTotal(
                            price,
                            sellingQuantityUnitSelected,
                            buyingQuantityUnitSelected,
                            customQuantity
                        )
                        shoppingList.add(
                            ShoppingItem(
                                "$price per $pricePerUnitText",
                                quantityText,
                                total
                            )
                        )
                        onPriceChange("")
                        onCustomQuantityChange("")
                        // Reset customQuantity after adding the item if needed
                        if (buyingQuantityUnitSelected == R.string.more_kg) {
                            onCustomQuantityChange(
                                "" // Reset if you're clearing the form upon adding
                            )
                        }

                        // toast("Item added to list")
                        Toast.makeText(
                            context,
                            "Item added to list",
                            Toast.LENGTH_SHORT
                        ).show()

                        keyboardController?.hide()
                    },
                    enabled = isAddItemButtonEnabled,
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_item),
                        fontFamily = fontFamily
                    )
                }
            }
        }

    }
}

fun calculateItemTotal(
    price: String,
    sellingQuantityUnitSelected: Int,
    buyingQuantityUnitSelected: Int,
    customQuantity: String
): Double {
    val pricePerUnit = price.toDoubleOrNull() ?: 0.0
    var totalItemCost = 0.0
    if (sellingQuantityUnitSelected == R.string.paao) {
        when (buyingQuantityUnitSelected) {
            R.string.one_paao -> {
                totalItemCost = pricePerUnit * 1
            }

            R.string.half_kg -> {
                totalItemCost = pricePerUnit * 2
            }

            R.string.three_paao -> {
                totalItemCost = pricePerUnit * 3
            }

            R.string.one_kg -> {
                totalItemCost = pricePerUnit * 4
            }

            R.string.more_kg -> {
                totalItemCost =
                    pricePerUnit * customQuantity.toDouble() * 4 // Corrected: customQuantity times 4
            }
        }
    }

    if (sellingQuantityUnitSelected == R.string.KG) {
        when (buyingQuantityUnitSelected) {
            R.string.one_paao -> {
                totalItemCost = pricePerUnit * 0.25
            }

            R.string.half_kg -> {
                totalItemCost = pricePerUnit * 0.5
            }

            R.string.three_paao -> {
                totalItemCost = pricePerUnit * 0.75
            }

            R.string.one_kg -> {
                totalItemCost = pricePerUnit * 1
            }

            R.string.more_kg -> {
                totalItemCost = pricePerUnit * customQuantity.toDouble()
            }
        }
    }

    if (sellingQuantityUnitSelected == R.string.dozen) {
        when (buyingQuantityUnitSelected) {
            R.string.one_paao -> {
                totalItemCost = pricePerUnit * 0.083
            }

            R.string.half_kg -> {
                totalItemCost = pricePerUnit * 0.167
            }

            R.string.three_paao -> {
                totalItemCost = pricePerUnit * 0.25
            }

            R.string.one_kg -> {
                totalItemCost = pricePerUnit * 0.333
            }

            R.string.more_kg -> {
                totalItemCost = pricePerUnit * customQuantity.toDouble()
            }
        }
    }

    return totalItemCost
}



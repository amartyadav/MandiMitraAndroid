package com.aatmamartya.mandimitra.views.ItemEntryScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aatmamartya.mandimitra.R
import com.aatmamartya.mandimitra.ShoppingItem
import com.aatmamartya.mandimitra.fontFamily

@Composable
fun TotalBillView(
    shoppingList: List<ShoppingItem>,
    isLargeDisplay: Boolean,
    onViewListClick: (List<ShoppingItem>) -> Unit,
) {
    // if total amount is 0, disable the view bill button
    val disableViewBillButton = shoppingList.sumOf { it.total }.toInt() == 0
    // Total Amount
    if (!isLargeDisplay) {
        Row(modifier = Modifier.padding(top = 20.dp, end = 20.dp)) {
            Spacer(modifier = Modifier.weight(1f)) // Spacer to ensure the TextField stays in center

            Text(
                text = "Total Amount  ₹",
                modifier = Modifier.padding(top = 10.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = fontFamily
            )
            Text(
                text = shoppingList.sumOf { it.total }.toString(),
                modifier = Modifier.padding(top = 10.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = fontFamily
            )

            Spacer(modifier = Modifier.weight(1f))


        }

        Row(modifier = Modifier.padding(end = 20.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onViewListClick(shoppingList) },
                modifier = Modifier.padding(top = 15.dp),
                enabled = !disableViewBillButton
            ) {
                Text(
                    text = stringResource(id = R.string.view_bill),
                    fontFamily = fontFamily
                )
            }
            Spacer(modifier = Modifier.weight(1f))


        }
    } else {
        Row(modifier = Modifier.padding(top = 20.dp, end = 20.dp)) {
            Spacer(modifier = Modifier.weight(1f)) // Spacer to ensure the TextField stays in center

            Text(
                text = "Total Amount  ₹",
                modifier = Modifier.padding(top = 10.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = fontFamily
            )
            Text(
                text = shoppingList.sumOf { it.total }.toString(),
                modifier = Modifier.padding(top = 10.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = fontFamily
            )


        }

        Row(modifier = Modifier.padding(end = 20.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onViewListClick(shoppingList) },
                modifier = Modifier.padding(top = 15.dp),
                enabled = !disableViewBillButton
            ) {
                Text(
                    text = stringResource(id = R.string.view_bill),
                    fontFamily = fontFamily
                )
            }


        }
    }
}



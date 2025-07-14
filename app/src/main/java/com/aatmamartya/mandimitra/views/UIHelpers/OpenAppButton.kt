package com.aatmamartya.mandimitra.views.UIHelpers

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aatmamartya.mandimitra.UPIHelpers.UPIAppScanner
import java.util.Locale

@Composable
fun OpenAppButton(appName: String, packageName: String) {
    val context = LocalContext.current
    val iconName = "${appName.lowercase(Locale.getDefault())}_icon"
    val drawableResourceId =
        context.resources.getIdentifier(iconName, "drawable", context.packageName)
    if (drawableResourceId != 0) { // If the drawable resource was found
        Image(
            painter = painterResource(id = drawableResourceId),
            contentDescription = "Open $appName",
            modifier = Modifier
                .clickable { UPIAppScanner.openUPIApp(context, packageName) }
                .size(80.dp) // Sets both width and height to 64.dp to ensure a square shape
                .padding(horizontal = 15.dp)
        )
    } else {
        Text("Open $appName")
    }
}
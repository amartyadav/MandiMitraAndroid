package com.aatmamartya.mandimitra.views.DetailedBill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aatmamartya.mandimitra.R
import com.aatmamartya.mandimitra.ShoppingItem

class ViewListFragment : Fragment() {
    private val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    private val fontName = GoogleFont("Nunito")

    private val fontFamily = FontFamily(
        Font(googleFont = fontName, fontProvider = provider)
    )

    private val mandiMitraColorScheme = lightColorScheme(
        primary = Color(0xFF4CAF50),
        onPrimary = Color.White,
        secondary = Color(0xFFFF9800),
        onSecondary = Color.White,
        background = Color(0xFFF5F5F5),
        onBackground = Color.Black,
        surface = Color.White,
        onSurface = Color.Black,
        error = Color(0xFFF44336),
        onError = Color.White
        // Define other colors if needed
    )

    // dark color scheme
    private val mandiMitraDarkColorScheme = darkColorScheme(
        primary = Color(0xFF81C784), // A lighter shade of your primary green for better visibility in dark mode
        onPrimary = Color.Black, // Black text on the lighter primary color
        secondary = Color(0xFFFFB74D), // A lighter shade of your secondary orange for better visibility
        onSecondary = Color.Black, // Black text on the lighter secondary color
        background = Color(0xFF303030), // A dark grey for background
        onBackground = Color.White, // White text on dark background
        surface = Color(0xFF424242), // Slightly lighter grey than background for surfaces
        onSurface = Color.White, // White text on dark surfaces
        error = Color(0xFFE57373), // A lighter shade of your error color for better visibility in dark mode
        onError = Color.Black // Black text on the lighter error color
        // Add more custom colors if needed
    )

    @Composable
    fun CustomTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        MaterialTheme(
            colorScheme = if (darkTheme) mandiMitraDarkColorScheme else mandiMitraColorScheme,
            content = content
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args =
            ViewListFragmentArgs.fromBundle(requireArguments()) // Retrieve arguments using Safe Args
        val shoppingList =
            args.shoppingList.toList() ?: listOf() // Convert Parcelable array to List

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.Default)
            setContent {
                ViewItemsScreen(shoppingList)
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ViewItemsScreen(shoppingList: List<ShoppingItem>) { // Placeholder for shoppingList
        val totalAmount = shoppingList.sumOf { it.total }
        val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

        CustomTheme(
        ) {
            Scaffold(topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { dispatcher?.onBackPressed() },
                                modifier = Modifier.offset(-10.dp, 0.dp)
                            ) {
                                Image(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Localized description"
                                )
                            }
                            Text(
                                text = "Mandi Mitra",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = fontFamily,

                                )
                        }
                    },
                )
            }) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    Column(
                        modifier = Modifier
                            .padding(bottom = 120.dp)
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = stringResource(id = R.string.bill_details),
                            style = MaterialTheme.typography.headlineMedium,
                            fontFamily = fontFamily,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(8.dp)

                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            thickness = DividerDefaults.Thickness,
                            color = DividerDefaults.color
                        )

                        // Using a regular Column instead of LazyColumn
                        Column {
                            shoppingList.forEachIndexed { index, item ->
                                ItemRow(index + 1, item)
                                HorizontalDivider(
                                    Modifier,
                                    DividerDefaults.Thickness,
                                    DividerDefaults.color
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(id = R.string.total),
                                style = MaterialTheme.typography.bodyLarge,
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "₹${String.format("%.2f", totalAmount)}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontFamily = fontFamily,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                fontWeight = FontWeight.Bold
                            )
                        }


                        // button to navigate to previous screen
                        Row {
                            Spacer(modifier = Modifier.weight(1f))
                            Button(
                                onClick = { navigateToPreviousScreen() },
                                modifier = Modifier.padding(top = 20.dp, start = 5.dp, end = 10.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.start_again),
                                    fontFamily = fontFamily
                                )
                            }
                        }

                    }
                }

            }
        }
    }

    @Composable
    fun ItemRow(index: Int, item: ShoppingItem) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shadowElevation = 2.dp,
            shape = RoundedCornerShape(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(90.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Item $index",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontFamily
                )
                Spacer(modifier = Modifier.width(40.dp))
                Column {
                    Text(
                        text = stringResource(id = R.string.rate) + ": ₹ ${item.rate}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp),
                        fontFamily = fontFamily
                    )
                    Text(
                        text = stringResource(id = R.string.qty) + ": ${item.quantity}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp),
                        fontFamily = fontFamily
                    )
                    Text(
                        text = stringResource(id = R.string.total) + ": ₹ ${item.total}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp),
                        fontFamily = fontFamily
                    )
                }
            }
        }
    }

    // You can add more functions to handle specific tasks like populating the list, etc.
    private fun navigateToPreviousScreen() {
        // Add code to navigate to the previous screen
        val action = ViewListFragmentDirections.actionViewListFragmentToItemEntryFragment()
        findNavController().navigate(action)
    }

}
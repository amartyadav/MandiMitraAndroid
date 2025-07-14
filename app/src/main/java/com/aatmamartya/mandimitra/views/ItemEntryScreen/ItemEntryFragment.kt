package com.aatmamartya.mandimitra.views.ItemEntryScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aatmamartya.mandimitra.R
import com.aatmamartya.mandimitra.ShoppingItem
import com.aatmamartya.mandimitra.fontFamily
import com.aatmamartya.mandimitra.mandiMitraColorScheme
import com.aatmamartya.mandimitra.mandiMitraDarkColorScheme
import com.aatmamartya.mandimitra.views.NewVersionAvailable.NewVersionModalHandler
import com.aatmamartya.mandimitra.views.UIHelpers.OpenAppButton


/**
 * A simple [Fragment] subclass.
 */
class ItemEntryFragment : Fragment() {

    private val shoppingList = mutableStateListOf<ShoppingItem>()


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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPreferences =
            requireContext().getSharedPreferences("MandiMitraPrefs", Context.MODE_PRIVATE)
        val currentAppVersion = sharedPreferences.getString("current_app_version", "")
        val latestAppVersion = sharedPreferences.getString("latest_app_version", "")

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                NewVersionModalHandler(
                    currentAppVersion = currentAppVersion ?: "",
                    latestAppVersion = latestAppVersion ?: "",
                    oUpdateClick = {
                        val playStoreIntent = Intent(
                            Intent.ACTION_VIEW,
                            "https://play.google.com/store/apps/details?id=com.aatmamartya.mandimitra".toUri()
                        )
                        context.startActivity(playStoreIntent)
                    }) {
                    ItemEntryScreen(
                        shoppingList = shoppingList,
                        onViewListClick = {
                            navigateToViewListFragment(it)
                        },
                        onViewSettingsClick = {
                            navigateToSettingsFragment()
                        }
                    )
                }
            }
        }
    }

    private fun navigateToViewListFragment(shoppingList: List<ShoppingItem>) {

        val action =
            ItemEntryFragmentDirections.actionItemEntryFragmentToViewListFragment(shoppingList.toTypedArray())
        findNavController().navigate(action)
    }

    private fun navigateToSettingsFragment() {
        val action = ItemEntryFragmentDirections.actionItemEntryFragmentToSettingsFragment()
        findNavController().navigate(action)
    }


    @SuppressLint("UnusedBoxWithConstraintsScope")
    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun ItemEntryScreen(
        onViewListClick: (List<ShoppingItem>) -> Unit,
        onViewSettingsClick: () -> Unit,
        shoppingList: MutableList<ShoppingItem>
    ) {
        var price by remember { mutableStateOf("") }
        var sellingQuantityUnitSelected by remember { mutableIntStateOf(R.string.paao) }


        val quantitySelections = listOf(
            R.string.one_paao,
            R.string.half_kg,
            R.string.three_paao,
            R.string.one_kg,
            R.string.more_kg
        )
        var buyingQuantityUnitSelected by remember { mutableIntStateOf(quantitySelections[0]) }

        var customQuantity by remember { mutableStateOf("") }
        // Determine if the "Add Item" button should be enabled
        val isAddItemButtonEnabled = remember(buyingQuantityUnitSelected, customQuantity) {
            if (buyingQuantityUnitSelected == R.string.more_kg) {
                customQuantity.isNotBlank() && customQuantity.toDoubleOrNull() != null
            } else {
                true // Enable the button if any other unit is selected
            }
        }


        var showMenu by remember { mutableStateOf(false) }
        val topappbariconimage = painterResource(R.drawable.final_app_icon_nobg)

        val keyboardController = LocalSoftwareKeyboardController.current

        // Callback function for when the price changes
        val onPriceChange: (String) -> Unit = { newPrice ->
            price = newPrice
        }

        // Callback function for when the selling quantity unit selection changes
        val onSellingQuantityUnitSelectedChange: (Int) -> Unit = { newUnit ->
            sellingQuantityUnitSelected = newUnit
        }




        CustomTheme {
            // Your Composable functions
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        title = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = topappbariconimage,
                                    contentDescription = "App Icon",
                                    modifier = Modifier
                                        .height(130.dp)
                                        .width(130.dp)
                                        .absoluteOffset(x = (-40).dp, y = (-2).dp)
                                )
                                Text(
                                    text = "Mandi Mitra",
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fontFamily,
                                    modifier = Modifier
                                        .absoluteOffset(x = (-70).dp, y = 0.dp)
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { onViewSettingsClick() }) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Localized description",
                                    tint = Color(0xFF003803) // Example dark green color
                                )
                            }
                        },
                    )
                }
            ) { innerPadding ->
                val focusManager = LocalFocusManager.current
//

                BoxWithConstraints(modifier = Modifier.padding(innerPadding)) {
                    val isLargeDisplay = maxWidth > 350.dp
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState()) // Make this Column scrollable
                            .padding(bottom = 160.dp) // Reserve space for the ad
                    ) {

                        ItemRateEntry(
                            price = price,
                            onPriceChange = onPriceChange,
                            sellingQuantityUnitSelected = sellingQuantityUnitSelected,
                            onSellingQuantityUnitSelectedChange = onSellingQuantityUnitSelectedChange,
                            quantityUnits = quantitySelections,
                            isLargeDisplay = isLargeDisplay,
                            focusManager = focusManager,
                            keyboardController = keyboardController
                        )


                        // Buying Quantity Selection

                        BuyingQuantitySelection(
                            onMoreKGChange = { newCustomQuantity ->
                                customQuantity = newCustomQuantity
                            },
                            buyingQuantityUnitSelected = buyingQuantityUnitSelected,
                            onBuyingQuantityUnitSelectedChange = { newUnit ->
                                buyingQuantityUnitSelected = newUnit
                            },
                            customQuantity = customQuantity,
                            onCustomQuantityChange = { newCustomQuantity ->
                                customQuantity = newCustomQuantity
                            },
                            quantitySelections = quantitySelections,
                            keyboardController = keyboardController,
                            isLargeDisplay = isLargeDisplay,
                            focusManager = focusManager,
                            price = price,
                            sellingQuantityUnitSelected = sellingQuantityUnitSelected,
                            shoppingList = shoppingList,
                            onPriceChange = onPriceChange,
                            isAddItemButtonEnabled = isAddItemButtonEnabled
                        )


                        HorizontalDivider(
                            modifier = Modifier.padding(top = 20.dp),
                            thickness = DividerDefaults.Thickness,
                            color = DividerDefaults.color
                        )

                        TotalBillView(
                            shoppingList = shoppingList,
                            isLargeDisplay = isLargeDisplay,
                            onViewListClick = onViewListClick
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(top = 20.dp),
                            thickness = DividerDefaults.Thickness,
                            color = DividerDefaults.color
                        )

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {

                        }
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            OpenAppButton(
                                appName = "GPay",
                                packageName = "com.google.android.apps.nbu.paisa.user"
                            )
                            OpenAppButton(
                                appName = "PhonePe",
                                packageName = "com.phonepe.app"
                            )
                            OpenAppButton(
                                appName = "Paytm",
                                packageName = "net.one97.paytm"
                            )
                            OpenAppButton(
                                appName = "Cred",
                                packageName = "com.dreamplug.androidapp"
                            )
                        }
//                        OpenAppButton(appName = "github", packageName = "com.github.android")


                    }

                }


            }
        }

    }
}

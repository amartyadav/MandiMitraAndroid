package com.aatmamartya.mandimitra.views.SettingsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.aatmamartya.mandimitra.R
import com.aatmamartya.mandimitra.mandiMitraColorScheme
import com.aatmamartya.mandimitra.mandiMitraDarkColorScheme

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using Compose
        return ComposeView(requireContext()).apply {
            setContent {
                SettingsScreen()
            }
        }
    }


    private val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    private val fontName = GoogleFont("Nunito")

    private val fontFamily = FontFamily(
        Font(googleFont = fontName, fontProvider = provider)
    )

    private val languageOptions = listOf("English", "Hindi")


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

    private var isThemeChangeInitiated = false


    @Preview
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SettingsScreen() {
        var language by remember { mutableStateOf("English") }
        var showLanguageDialog by remember { mutableStateOf(false) }

        val context = LocalContext.current

        var showThemeDialog by remember { mutableStateOf(false) }
        val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
        val activity = LocalActivity

        val customTabsIntent = CustomTabsIntent.Builder().build()

        CustomTheme {
            Scaffold(
                topBar = {
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
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Localized description"
                                    )
                                }
                                Text(
                                    text = "Settings",
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = fontFamily,
                                )
                            }
                        },

                        )
                }
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState()) // Make this Column scrollable
                            .padding(
                                bottom = 160.dp,
                                start = 20.dp,
                                end = 20.dp
                            ) // Reserve space for the ad
                    ) {
                        Text(
                            "General",
                            modifier = Modifier.padding(bottom = 8.dp, top = 10.dp),
                            fontSize = 30.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Row(
                            modifier = Modifier.padding(top = 10.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.language_icon),
                                contentDescription = "Language Icon",
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Language (Coming Soon)",
//                            modifier = Modifier.clickable { showLanguageDialog = true },
                                fontSize = 20.sp
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                            thickness = DividerDefaults.Thickness, color = DividerDefaults.color
                        )

                        if (showLanguageDialog) {
                            LanguageSelectionDialog(
                                selectedLanguage = language,
                                onDismissRequest = { showLanguageDialog = false },
                                onLanguageSelected = { selected ->
                                    language = selected
                                    showLanguageDialog = false
                                    // Update the language setting here
                                }
                            )
                        }

//                    Row(
//                        modifier = Modifier.padding(top = 10.dp),
//                    ) {
//                        Icon(
//                            Icons.Rounded.Settings,
//                            contentDescription = "Theme icon",
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            "Dark Mode (Coming Soon)",
////                            modifier = Modifier
////                                .clickable { showThemeDialog = true },
//                            fontSize = 20.sp
//                        )
//                    }
//
//                    Divider(
//                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
//                    )


                        Row(
                            modifier = Modifier.padding(top = 10.dp),
                        ) {
                            Icon(
                                Icons.Rounded.Star,
                                contentDescription = "Rate the app icon",
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Rate the app",
                                fontSize = 20.sp,
                                modifier = Modifier.clickable {
                                    customTabsIntent.launchUrl(
                                        context,
                                        "https://play.google.com/store/apps/details?id=com.aatmamartya.mandimitra".toUri()
                                    )
                                },
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                            thickness = DividerDefaults.Thickness, color = DividerDefaults.color
                        )

                        Row(
                            modifier = Modifier.padding(top = 10.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.sponsor_icon),
                                contentDescription = "Support me icon",
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Support Me (Coming Soon)",
                                fontSize = 20.sp,
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                            thickness = DividerDefaults.Thickness, color = DividerDefaults.color
                        )

                        Text(
                            "About",
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontSize = 30.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Row(
                            modifier = Modifier.padding(top = 10.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.privacy_policy_icon),
                                contentDescription = "Privacy Policy icon",
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Privacy Policy",
                                fontSize = 20.sp,
                                modifier = Modifier.clickable {
                                    customTabsIntent.launchUrl(
                                        context,
                                        "https://docs.google.com/document/d/1xjscbL1W_GOkL2C4xp_o-ILJhdr_hRq5iTRiOJVcN9w/edit?usp=sharing".toUri()
                                    )
                                },
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                            thickness = DividerDefaults.Thickness, color = DividerDefaults.color
                        )

                        Row(
                            modifier = Modifier.padding(top = 10.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.release_notes_icon),
                                contentDescription = "Release Notes icon",
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Release Notes",
                                fontSize = 20.sp,
                                modifier = Modifier.clickable {
                                    customTabsIntent.launchUrl(
                                        context,
                                        "https://aatmamartya.notion.site/Mandi-Mitra-Release-Notes-c498562297cc48ada53948155e3642b0?pvs=4".toUri()
                                    )
                                },
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                            thickness = DividerDefaults.Thickness, color = DividerDefaults.color
                        )

                        Row(
                            modifier = Modifier.padding(top = 10.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.website_icon),
                                contentDescription = "Developer Website icon",
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "View Website",
                                fontSize = 20.sp,
                                modifier = Modifier.clickable {
                                    customTabsIntent.launchUrl(
                                        context,
                                        "https://amartyadav.com".toUri()
                                    )
                                },
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                            thickness = DividerDefaults.Thickness, color = DividerDefaults.color
                        )

                        Row(
                            modifier = Modifier.padding(top = 10.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.github_icon),
                                contentDescription = "Github icon",
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "View My GitHub",
                                fontSize = 20.sp,
                                modifier = Modifier.clickable {
                                    customTabsIntent.launchUrl(
                                        context,
                                        "https://github.com/amartyadav".toUri()
                                    )
                                },
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                            thickness = DividerDefaults.Thickness, color = DividerDefaults.color
                        )

                        // add a copyright footer
                        Text(
                            "© 2025 All Rights Reserved \n    Amartya Yadav",
                            modifier = Modifier.padding(top = 16.dp),
                            fontSize = 16.sp,
                            color = Color.Gray
                        )


                    }

                }

            }
        }

    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LanguageSelectionDialog(
        selectedLanguage: String,
        onDismissRequest: () -> Unit,
        onLanguageSelected: (String) -> Unit
    ) {
        val languageOptions = listOf("English", "Hindi")

        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            title = { Text(text = "Choose Language") },
            text = {
                Column {
                    languageOptions.forEach { language ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedLanguage == language,
                                onClick = { onLanguageSelected(language) }
                            )
                            Text(text = language)
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { onDismissRequest() }) {
                    Text("OK")
                }
            }
        )
    }


}

package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.configureAppCheck
import com.example.ui.AppTab
import com.example.ui.AstroOracleViewModel
import com.example.ui.components.CelestialBackground
import com.example.ui.screens.DailyInsightScreen
import com.example.ui.screens.NumerologyScreen
import com.example.ui.screens.OracleChatScreen
import com.example.ui.screens.ProfileEditDialog
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.TarotScreen
import com.example.ui.screens.VedicScreen
import com.example.ui.theme.CelestialGold
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PaleGold
import com.example.ui.theme.SurfaceCosmic
import com.example.ui.theme.SurfaceVariantCosmic
import com.example.ui.theme.TextMutedCosmic

class MainActivity : ComponentActivity() {

    private val viewModel: AstroOracleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // MANDATORY STEP 1: Configure App Check with launch intent before any UI or Firebase operations
        configureAppCheck(this, intent)

        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                AstroOracleApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AstroOracleApp(viewModel: AstroOracleViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val showProfileDialog by viewModel.showProfileDialog.collectAsState()

    // Handle back button: if on sub-tab, return to DAILY tab
    BackHandler(enabled = currentTab != AppTab.DAILY) {
        viewModel.selectTab(AppTab.DAILY)
    }

    if (showProfileDialog) {
        ProfileEditDialog(
            viewModel = viewModel,
            onDismiss = { viewModel.setShowProfileDialog(false) }
        )
    }

    CelestialBackground {
        Scaffold(
            contentWindowInsets = WindowInsets.safeDrawing,
            containerColor = Color.Transparent,
            bottomBar = {
                AstroBottomNavigationBar(
                    currentTab = currentTab,
                    onTabSelected = { viewModel.selectTab(it) }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentTab) {
                    AppTab.DAILY -> DailyInsightScreen(viewModel = viewModel)
                    AppTab.TAROT -> TarotScreen(viewModel = viewModel)
                    AppTab.NUMEROLOGY -> NumerologyScreen(viewModel = viewModel)
                    AppTab.VEDIC -> VedicScreen(viewModel = viewModel)
                    AppTab.ORACLE -> OracleChatScreen(viewModel = viewModel)
                    AppTab.PROFILE -> ProfileScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun AstroBottomNavigationBar(
    currentTab: AppTab,
    onTabSelected: (AppTab) -> Unit,
    modifier: Modifier = Modifier
) {
    // 5 primary navigation tabs for ergonomic bottom bar
    val mainTabs = listOf(
        AppTab.DAILY,
        AppTab.TAROT,
        AppTab.NUMEROLOGY,
        AppTab.VEDIC,
        AppTab.ORACLE
    )

    NavigationBar(
        modifier = modifier.testTag("astro_bottom_navigation"),
        containerColor = SurfaceCosmic,
        tonalElevation = 8.dp
    ) {
        mainTabs.forEach { tab ->
            val isSelected = tab == currentTab
            val icon: ImageVector = when (tab) {
                AppTab.DAILY -> Icons.Default.AutoAwesome
                AppTab.TAROT -> Icons.Default.Style
                AppTab.NUMEROLOGY -> Icons.Default.FormatListNumbered
                AppTab.VEDIC -> Icons.Default.SelfImprovement
                AppTab.ORACLE -> Icons.AutoMirrored.Filled.Chat
                AppTab.PROFILE -> Icons.Default.Person
            }

            val shortTitle = when (tab) {
                AppTab.DAILY -> "Daily"
                AppTab.TAROT -> "Tarot"
                AppTab.NUMEROLOGY -> "Numbers"
                AppTab.VEDIC -> "Vedic"
                AppTab.ORACLE -> "Chat"
                AppTab.PROFILE -> "Chart"
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = tab.title,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(
                        text = shortTitle,
                        fontFamily = CinzelFontFamily,
                        fontSize = 10.sp,
                        maxLines = 1
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = CelestialGold,
                    selectedTextColor = PaleGold,
                    indicatorColor = SurfaceVariantCosmic,
                    unselectedIconColor = TextMutedCosmic,
                    unselectedTextColor = TextMutedCosmic
                ),
                modifier = Modifier.testTag("tab_${tab.name.lowercase()}")
            )
        }
    }
}

package com.jtautomation02.foodcravies.ui.features.commoncomposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jtautomation02.foodcravies.ui.theme.BackgroundLight
import com.jtautomation02.foodcravies.ui.theme.OrangePrimary
import com.jtautomation02.foodcravies.ui.theme.TextSecondaryLight


// --- THIS IS THE DEFINITION ---
enum class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    // --- AND HERE IS THE LINE ---
    HOME(
        "home", // <-- This is BottomNavItem.HOME.route
        "Home",
        Icons.Filled.Home,
        Icons.Outlined.Home
    ),
    ORDERS(
        "orders",
        "Orders",
        Icons.Filled.AccountBox,
        Icons.Outlined.AccountBox
    ),
    SEARCH(
        "search",
        "Search",
        Icons.Filled.Search,
        Icons.Outlined.Search
    ),
    ACCOUNT(
        "account",
        "Account",
        Icons.Filled.Person,
        Icons.Outlined.Person
    )
}

@Composable
fun AppBottomNavigation(
    // --- CHANGE 1: ADD PARAMETERS ---
    currentRoute: String, // The NavController will provide this
    onNavigate: (String) -> Unit, // This will call navController.navigate()
    modifier: Modifier = Modifier
) {
    // --- REMOVED ---
    // var selectedItem by remember { mutableIntStateOf(0) }

    // We use the robust BottomNavItem enum
    val items = listOf(
        BottomNavItem.HOME,
        BottomNavItem.ORDERS,
        BottomNavItem.SEARCH,
        BottomNavItem.ACCOUNT
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = BackgroundLight,
        shadowElevation = 8.dp
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            NavigationBar(
                containerColor = BackgroundLight,
                contentColor = TextSecondaryLight,
                tonalElevation = 0.dp
            ) {
                items.forEach { item ->
                    // --- CHANGE 2: UPDATE LOGIC ---
                    val isSelected = currentRoute == item.route // Check by route, not index

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { onNavigate(item.route) }, // Call the navigation lambda
                        icon = {
                            Icon(
                                // Get icons from the enum item
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(
                                text = item.title, // Get title from the enum item
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = OrangePrimary,
                            selectedTextColor = OrangePrimary,
                            unselectedIconColor = TextSecondaryLight,
                            unselectedTextColor = TextSecondaryLight,
                            indicatorColor = BackgroundLight
                        )
                    )
                }
            }
            // ... (Your Home indicator bar remains the same)
            Box(
                modifier = Modifier
                    .width(130.dp)
                    .height(4.dp)
                    .padding(bottom = 2.dp)
                    .clip(RoundedCornerShape(100))
                    .background(Color(0xFFD1D5DB))
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

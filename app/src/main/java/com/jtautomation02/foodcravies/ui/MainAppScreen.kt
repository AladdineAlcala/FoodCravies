package com.jtautomation02.foodcravies.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jtautomation02.foodcravies.ui.features.account.AccountScreen
import com.jtautomation02.foodcravies.ui.features.commoncomposables.AppBottomNavigation
import com.jtautomation02.foodcravies.ui.features.commoncomposables.BottomNavItem
import com.jtautomation02.foodcravies.ui.features.home.HomeScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(){
    // This NavController is for the main app sections (bottom bar)
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            // Get the current route to highlight the correct icon
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: BottomNavItem.HOME.route

            // Pass the state and click handler to your component
            AppBottomNavigation(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    // This is where navigation actually happens
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        // This NavHost swaps the screen content
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.HOME.route,
            modifier = Modifier.padding(innerPadding) // Apply padding from Scaffold
        ) {
            // Home Screen
            composable(BottomNavItem.HOME.route) {
                HomeScreen()
            }

            // Orders Screen (Placeholder)
            composable(BottomNavItem.ORDERS.route) {
                Text("Orders Screen", modifier = Modifier.fillMaxSize())
            }

            // Search Screen (Placeholder)
            composable(BottomNavItem.SEARCH.route) {
                Text("Search Screen", modifier = Modifier.fillMaxSize())
            }

            // --- THIS IS THE INTEGRATION ---
            // Account Screen
            composable(BottomNavItem.ACCOUNT.route) {
                AccountScreen(
                    // Pass navigation lambdas for *internal* clicks
                    onNavigateBack = { navController.popBackStack() },
                    onNavigate = { route -> navController.navigate(route) }
                )
            }
        }
    }
}
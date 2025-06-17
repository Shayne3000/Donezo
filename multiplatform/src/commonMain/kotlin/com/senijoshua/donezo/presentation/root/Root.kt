package com.senijoshua.donezo.presentation.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.senijoshua.donezo.presentation.tasks.tasksGraph
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private const val START_INDEX = 0

/**
 * App-level composable for holding app-level UI components and logic
 */
@Composable
fun Root(
    navController: NavHostController = rememberNavController()
) {
    val startGraph = appLevelRoutes[START_INDEX].route
    var selectedDestinationIndex by rememberSaveable { mutableIntStateOf(START_INDEX) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                appLevelRoutes.forEachIndexed { index, appLevelRoute ->
                    val itemName = stringResource(appLevelRoute.name)

                    NavigationBarItem(
                        modifier = Modifier,
                        selected = index == selectedDestinationIndex,
                        onClick = {
                            navController.navigate(appLevelRoute.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            selectedDestinationIndex = index
                        },
                        icon = {
                            Icon(
                                painter = painterResource(appLevelRoute.icon),
                                contentDescription = itemName
                            )
                        },
                        label = {
                            Text(text = itemName)
                        },
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(modifier = Modifier.padding(paddingValues), navController = navController, startDestination = startGraph) {
            // App-level nested nav graphs
            tasksGraph()
        }
    }
}



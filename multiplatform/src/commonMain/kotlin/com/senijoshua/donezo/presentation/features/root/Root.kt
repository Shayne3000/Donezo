package com.senijoshua.donezo.presentation.features.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.senijoshua.donezo.presentation.features.completed.completedGraph
import com.senijoshua.donezo.presentation.features.tasks.tasksGraph
import com.senijoshua.donezo.presentation.theme.dimensions
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
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .padding(
                        vertical = MaterialTheme.dimensions.xxSmall,
                        horizontal = MaterialTheme.dimensions.xSmall,
                    )
                    .clip(RoundedCornerShape(MaterialTheme.dimensions.small)),
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ) {
                appLevelRoutes.forEachIndexed { index, appLevelRoute ->
                    val itemName = stringResource(appLevelRoute.name)

                    val isTabSelected = index == selectedDestinationIndex

                    NavigationBarItem(
                        selected = isTabSelected,
                        colors = NavigationBarItemColors(
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                            selectedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                            selectedIconColor = MaterialTheme.colorScheme.onSurface,
                            selectedTextColor = MaterialTheme.colorScheme.onSurface,
                            disabledIconColor = MaterialTheme.colorScheme.onSurface,
                            disabledTextColor = MaterialTheme.colorScheme.onSurface
                        ),
                        onClick = {
                            selectedDestinationIndex = index
                            navController.navigate(appLevelRoute.route) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(appLevelRoute.icon),
                                contentDescription = itemName
                            )
                        },
                        label = {
                            Text(
                                text = itemName,
                                style = MaterialTheme.typography.labelSmall,
                            )
                        },
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = startGraph
        ) {
            // App-level nested nav graphs
            tasksGraph()
            completedGraph()
        }
    }
}

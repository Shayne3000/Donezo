package com.senijoshua.donezo.presentation.tasks

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.senijoshua.donezo.presentation.root.TasksGraph
import kotlinx.serialization.Serializable

@Serializable
object TasksRoute

/**
 * Nav graph for screens in the Tasks tab
 */
fun NavGraphBuilder.tasksGraph() {
    navigation<TasksGraph>(startDestination = TasksRoute) {
        composable<TasksRoute> {
            TasksScreen()
        }
    }
}

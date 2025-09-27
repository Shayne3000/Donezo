package com.senijoshua.donezo.presentation.features.completed

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.senijoshua.donezo.presentation.features.root.CompletedGraph
import kotlinx.serialization.Serializable

@Serializable
object CompletedRoute

/**
 * Nav graph for screens in the Completed tab
 */
fun NavGraphBuilder.completedGraph() {
    navigation<CompletedGraph>(startDestination = CompletedRoute) {
        composable<CompletedRoute> {
            CompletedScreen()
        }
    }
}

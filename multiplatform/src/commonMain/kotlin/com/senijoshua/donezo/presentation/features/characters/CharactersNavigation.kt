package com.senijoshua.donezo.presentation.features.characters

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.senijoshua.donezo.presentation.features.characters.detail.CharactersDetailScreen
import com.senijoshua.donezo.presentation.features.characters.list.CharactersListScreen
import com.senijoshua.donezo.presentation.features.root.CharactersGraph
import kotlinx.serialization.Serializable

@Serializable
object CharactersListRoute

@Serializable
data class CharactersDetailRoute(val id: Int)

/**
 * Nav graph for screens in the Characters tab
 */
fun NavGraphBuilder.charactersGraph(navController: NavController) {
    navigation<CharactersGraph>(startDestination = CharactersListRoute) {
        composable<CharactersListRoute> {
            CharactersListScreen(navigateToDetail = { id ->
                navController.navigate(CharactersDetailRoute(id))
            })
        }
        composable<CharactersDetailRoute> {
            CharactersDetailScreen(onBackPressed = {
                navController.popBackStack()
            })
        }
    }
}

package com.senijoshua.donezo.presentation.root

import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.characters_tab_title
import donezo.multiplatform.generated.resources.completed_tab_title
import donezo.multiplatform.generated.resources.ic_groups
import donezo.multiplatform.generated.resources.ic_list_alt
import donezo.multiplatform.generated.resources.ic_list_alt_check
import donezo.multiplatform.generated.resources.tasks_tab_title
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

/**
 * App-level Navigation concerns
 */

/**
 * List of [AppLevelRoute]s for the navigation items in the
 * bottom navigation bar
 */
val appLevelRoutes = buildList {
    add(AppLevelRoute(Res.string.tasks_tab_title, TasksGraph, Res.drawable.ic_list_alt))
    add(AppLevelRoute(Res.string.completed_tab_title, CompletedGraph, Res.drawable.ic_list_alt_check))
    add(AppLevelRoute(Res.string.characters_tab_title, CharactersGraph, Res.drawable.ic_groups))
}

/**
 * App-level nested nav graph routes for the destinations hosted in the bottom navigation bar
 */
@Serializable
object TasksGraph

@Serializable
object CompletedGraph

@Serializable
object CharactersGraph

/**
 * App-level nested nav graph route data
 */
data class AppLevelRoute<T : Any>(
    val name: StringResource,
    val route: T,
    val icon: DrawableResource
)



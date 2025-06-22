package com.senijoshua.donezo.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Backing type of the dimension composition local that can be
 * accessed in any descendant below its declaration point.
 * A.K.A Composition Local type
 */
@Immutable
data class Dimensions(
    val xxSmall: Dp = 2.dp,
    val xSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val xLarge: Dp = 64.dp,
)

/**
 * Dimensions CompositionLocal whose value will be
 * accessed in the theme composable and its children
 */
val LocalDimensions = staticCompositionLocalOf { Dimensions() }

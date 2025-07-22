package com.senijoshua.donezo.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import network.chaintech.sdpcomposemultiplatform.sdp


/**
 * Dimensions CompositionLocal with default values that can be
 * accessed in any descendant below its declaration point
 * A.K.A Composition Local type
 */
val LocalDimensions = staticCompositionLocalOf { Dimensions() }

/**
 * Implementation of the Dimension composition local
 * with updated scalable values that will be
 * accessed in the theme composable and its descendant
 */
@Composable
internal fun donezoDimensions(): Dimensions {
    return Dimensions(
        xxxSmall = 2.sdp,
        xxSmall = 4.sdp,
        xSmall = 8.sdp,
        small = 16.sdp,
        medium = 24.sdp,
        large = 32.sdp,
        xLarge = 48.sdp,
        xxLarge = 56.sdp,
        xxxLarge = 64.sdp,
    )
}

/**
 * Backing type of the dimension composition local.
 */
@Immutable
data class Dimensions(
    val xxxSmall: Dp = 2.dp,
    val xxSmall: Dp = 4.dp,
    val xSmall: Dp = 8.dp,
    val small: Dp = 16.dp,
    val medium: Dp = 24.dp,
    val large: Dp = 32.dp,
    val xLarge: Dp = 48.dp,
    val xxLarge: Dp = 56.dp,
    val xxxLarge: Dp = 64.dp,
)

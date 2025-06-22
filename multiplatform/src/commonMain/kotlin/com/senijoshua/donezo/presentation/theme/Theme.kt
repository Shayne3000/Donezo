package com.senijoshua.donezo.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun DonezoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalDimensions provides Dimensions()) {
        // Can now implicitly access Dimensions in any descendant
        MaterialTheme(
            colorScheme = colorScheme,
            typography = DonezoTypography(),
            content = content
        )
    }
}

/**
 * [DonezoTheme] object that helps provides
 * access to theming systems.
 */
object DonezoTheme{
    val dimensions: Dimensions
        @Composable
        get() = LocalDimensions.current
}

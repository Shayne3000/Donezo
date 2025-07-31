package com.senijoshua.donezo.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

internal val lightColors = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    surfaceContainer = surfaceContainerLight
)

internal val darkColors = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    surfaceContainer = surfaceContainerDark
)

@Composable
fun DonezoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColors
        else -> lightColors
    }

    CompositionLocalProvider(LocalDimensions provides donezoDimensions()) {
        // Can now implicitly access Dimensions in any descendant
        MaterialTheme(
            colorScheme = colorScheme,
            typography = DonezoTypography(),
            content = content
        )
    }
}


/**
 * Extension that adds the Dimension
 * theming system to [MaterialTheme] and facilitates
 * access to said system wherever MaterialTheme is being
 * used i.e. within descendants of DonezoTheme.
 */
val MaterialTheme.dimensions: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current

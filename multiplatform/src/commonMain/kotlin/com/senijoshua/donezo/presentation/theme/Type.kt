package com.senijoshua.donezo.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import donezo.multiplatform.generated.resources.Lexend_Bold
import donezo.multiplatform.generated.resources.Lexend_ExtraBold
import donezo.multiplatform.generated.resources.Lexend_ExtraLight
import donezo.multiplatform.generated.resources.Lexend_Light
import donezo.multiplatform.generated.resources.Lexend_Medium
import donezo.multiplatform.generated.resources.Lexend_Regular
import donezo.multiplatform.generated.resources.Lexend_SemiBold
import donezo.multiplatform.generated.resources.Lexend_Thin
import donezo.multiplatform.generated.resources.Res
import network.chaintech.sdpcomposemultiplatform.ssp
import org.jetbrains.compose.resources.Font

@Composable
private fun Lexend() = FontFamily(
    Font(resource = Res.font.Lexend_ExtraLight, weight = FontWeight.ExtraLight),
    Font(resource = Res.font.Lexend_Light, weight = FontWeight.Light),
    Font(resource = Res.font.Lexend_Thin, weight = FontWeight.Thin),
    Font(resource = Res.font.Lexend_Regular, weight = FontWeight.Normal),
    Font(resource = Res.font.Lexend_Medium, weight = FontWeight.Medium),
    Font(resource = Res.font.Lexend_SemiBold, weight = FontWeight.SemiBold),
    Font(resource = Res.font.Lexend_Bold, weight = FontWeight.Bold),
    Font(resource = Res.font.Lexend_ExtraBold, weight = FontWeight.ExtraBold)
)

@Composable
internal fun DonezoTypography(): Typography {
    val lexend = Lexend()

    return Typography(
        //// Headers ////
        // Screen header
        headlineMedium = TextStyle(
            fontFamily = lexend,
            fontWeight = FontWeight.Normal,
            fontSize = 28.ssp,
            lineHeight = 38.ssp
        ),

        // Dialog title
        titleLarge = TextStyle(
            fontFamily = lexend,
            fontWeight = FontWeight.Medium,
            fontSize = 22.ssp,
            lineHeight = 28.ssp
        ),

        // Card title
        titleMedium = TextStyle(
            fontFamily = lexend,
            fontWeight = FontWeight.Medium,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            letterSpacing = 0.15.ssp
        ),

        //// Body ////
        bodyLarge = TextStyle(
            fontFamily = lexend,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            letterSpacing = 0.5.ssp
        ),

        bodyMedium = TextStyle(
            fontFamily = lexend,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 20.ssp,
            letterSpacing = 0.25.ssp
        ),

        bodySmall = TextStyle(
            fontFamily = lexend,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 16.ssp,
            letterSpacing = 0.4.ssp
        ),

        //// Buttons ////
        labelLarge = TextStyle(
            fontFamily = lexend,
            fontWeight = FontWeight.Medium,
            fontSize = 14.ssp,
            lineHeight = 20.ssp,
            letterSpacing = 0.1.ssp
        ),

        labelMedium = TextStyle(
            fontFamily = lexend,
            fontWeight = FontWeight.Medium,
            fontSize = 12.ssp,
            lineHeight = 26.ssp,
            letterSpacing = 0.5.ssp
        ),

        labelSmall = TextStyle(
            fontFamily = lexend,
            fontWeight = FontWeight.Medium,
            fontSize = 11.ssp,
            lineHeight = 16.ssp,
            letterSpacing = 0.5.ssp
        ),
    )
}

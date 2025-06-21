package com.senijoshua.donezo.presentation.theme

import androidx.compose.runtime.Composable
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
import org.jetbrains.compose.resources.Font

@Composable
fun Lexend() = FontFamily(
    Font(resource = Res.font.Lexend_ExtraLight, weight = FontWeight.ExtraLight),
    Font(resource = Res.font.Lexend_Light, weight = FontWeight.Light),
    Font(resource = Res.font.Lexend_Thin, weight = FontWeight.Thin),
    Font(resource = Res.font.Lexend_Regular, weight = FontWeight.Normal),
    Font(resource = Res.font.Lexend_Medium, weight = FontWeight.Medium),
    Font(resource = Res.font.Lexend_SemiBold, weight = FontWeight.SemiBold),
    Font(resource = Res.font.Lexend_Bold, weight = FontWeight.Bold),
    Font(resource = Res.font.Lexend_ExtraBold, weight = FontWeight.ExtraBold)
)



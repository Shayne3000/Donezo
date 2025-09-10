package com.senijoshua.donezo.presentation.completed

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CompletedScreen() {

}

@Composable
private fun CompletedContent() {

}

@Preview
@Composable
private fun CompletedScreenLightPreview() {
    DonezoTheme {
        Surface {

        }
    }
}

@Preview
@Composable
private fun CompletedScreenDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {

        }
    }
}

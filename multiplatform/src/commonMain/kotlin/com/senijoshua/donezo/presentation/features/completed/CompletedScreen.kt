package com.senijoshua.donezo.presentation.features.completed

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CompletedScreen(
    viewModel: CompletedViewModel = koinViewModel()
) {
    // Collect UiState as lifecycle

    // internal content composable

}

@Composable
private fun CompletedContent() {
    // Setup snackbar

    // Setup event handling for any errors

    // Setup Scaffold
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

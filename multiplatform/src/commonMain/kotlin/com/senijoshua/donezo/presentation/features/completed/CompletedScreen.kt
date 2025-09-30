package com.senijoshua.donezo.presentation.features.completed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.utils.getGenericErrorMessage
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CompletedScreen(
    viewModel: CompletedViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvent = viewModel.uiEvent

    CompletedContent(
        uiState = uiState,
        uiEvent = uiEvent
    )
}

@Composable
private fun CompletedContent(
    modifier: Modifier = Modifier,
    uiState: CompletedUiState,
    uiEvent: SharedFlow<CompletedUiEvent>
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val genericErrorMessage = getGenericErrorMessage()

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) { data ->
            Snackbar(
                snackbarData = data,
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        }
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

        }
    }

    LaunchedEffect(Unit) {
        uiEvent.collect { events ->
            when (events) {
                is CompletedUiEvent.Error -> {
                    val message = events.message ?: genericErrorMessage
                    snackBarHostState.showSnackbar(message)
                }
            }
        }
    }
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

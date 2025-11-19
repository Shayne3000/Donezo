package com.senijoshua.donezo.presentation.features.characters.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.senijoshua.donezo.presentation.features.characters.CharactersUiEvent
import com.senijoshua.donezo.presentation.features.characters.CharactersViewModel
import com.senijoshua.donezo.presentation.features.characters.DetailUiState
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.utils.UiEventHandler
import com.senijoshua.donezo.presentation.utils.getGenericErrorMessage
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharactersDetailScreen(
    onBackPressed: () -> Unit,
    viewModel: CharactersViewModel = koinViewModel()
) {
    val uiState by viewModel.detailUiState.collectAsStateWithLifecycle()
    val uiEvent = viewModel.uiEvent

    CharactersDetailContent(
        uiState = uiState,
        uiEvent = uiEvent,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun CharactersDetailContent(
    uiState: DetailUiState,
    uiEvent: SharedFlow<CharactersUiEvent>,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,

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
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(paddingValues)
        ) {

        }
    }

    UiEventHandler(eventFlow = uiEvent) { event ->
        when (event) {
            is CharactersUiEvent.Error -> {
                val message = event.message ?: genericErrorMessage
                snackBarHostState.showSnackbar(message)
            }
        }
    }
}

@Preview
@Composable
private fun CharactersDetailLightPreview() {
    DonezoTheme {

    }
}

@Preview
@Composable
private fun CharactersDetailDarkPreview() {
    DonezoTheme(darkTheme = true) {

    }
}


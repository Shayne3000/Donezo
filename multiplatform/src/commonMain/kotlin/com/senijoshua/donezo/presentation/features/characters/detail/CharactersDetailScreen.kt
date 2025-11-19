package com.senijoshua.donezo.presentation.features.characters.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.senijoshua.donezo.presentation.features.characters.CharactersUiEvent
import com.senijoshua.donezo.presentation.features.characters.CharactersViewModel
import com.senijoshua.donezo.presentation.features.characters.DetailUiState
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharactersDetailContent(
    uiState: DetailUiState,
    uiEvent: SharedFlow<CharactersUiEvent>,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val genericErrorMessage = getGenericErrorMessage()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                title = {}
            )
        },
        snackbarHost = {
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
            when (uiState) {
                is DetailUiState.Loading -> {
                    // Screen with shimmer
                }

                is DetailUiState.Success -> {
                    val character = uiState.character

                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MaterialTheme.dimensions.custom112)
                            .padding(horizontal = MaterialTheme.dimensions.small)
                            .clip(RoundedCornerShape(MaterialTheme.dimensions.small)),
                        model = character.thumbnailUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
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


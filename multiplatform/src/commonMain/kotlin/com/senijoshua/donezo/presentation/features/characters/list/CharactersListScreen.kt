package com.senijoshua.donezo.presentation.features.characters.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.senijoshua.donezo.presentation.components.EmptyState
import com.senijoshua.donezo.presentation.features.characters.Character
import com.senijoshua.donezo.presentation.features.characters.CharactersUiEvent
import com.senijoshua.donezo.presentation.features.characters.CharactersViewModel
import com.senijoshua.donezo.presentation.features.characters.ListUiState
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import com.senijoshua.donezo.presentation.utils.getGenericErrorMessage
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.characters_header
import donezo.multiplatform.generated.resources.empty_character_list
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharactersListScreen(
    navigateToDetail: (Int) -> Unit,
    viewModel: CharactersViewModel = koinViewModel()
) {
    val characterListUiState by viewModel.listUiState.collectAsStateWithLifecycle()
    val charactersUiEvent = viewModel.uiEvent

    CharactersListContent(
        state = characterListUiState,
        uiEvent = charactersUiEvent,
        onCharacterItemClicked = { id -> navigateToDetail(id) }
    )
}

@Composable
private fun CharactersListContent(
    modifier: Modifier = Modifier,
    state: ListUiState,
    uiEvent: SharedFlow<CharactersUiEvent>,
    onCharacterItemClicked: (Int) -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val genericErrorMessage = getGenericErrorMessage()

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState) { data ->
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
            Text(
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.dimensions.small,
                        top = MaterialTheme.dimensions.small
                    )
                    .fillMaxWidth(),
                text = stringResource(Res.string.characters_header),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            when (state) {
                is ListUiState.Loading -> {
                    // Loading shimmer
                }

                is ListUiState.Success -> {
                    val charactersList = state.characters

                    if (charactersList.isEmpty()) {
                        EmptyState(stringResource(Res.string.empty_character_list))
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = MaterialTheme.dimensions.small)
                        ) {
                            items(
                                items = charactersList,
                                key = { character -> character.id }) { character ->
                                // Character item
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        uiEvent.collect { event ->
            when (event) {
                is CharactersUiEvent.Error -> {
                    val message = event.message ?: genericErrorMessage
                    snackbarHostState.showSnackbar(message)
                }
            }
        }
    }
}

@Composable
private fun CharacterItem(
    character: Character,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(modifier = Modifier, model = character.thumbnailUrl, contentDescription = null)
        Column {  }
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier) {

}

@Preview
@Composable
private fun CharactersListLightPreview() {
    DonezoTheme {
        CharactersListContent(
            state = ListUiState.Success(characters = emptyList()),
            uiEvent = MutableSharedFlow()
        )
    }
}

@Preview
@Composable
private fun CharactersListDarkPreview() {
    DonezoTheme(darkTheme = true) {
        CharactersListContent(
            state = ListUiState.Success(characters = emptyList()),
            uiEvent = MutableSharedFlow()
        )
    }
}

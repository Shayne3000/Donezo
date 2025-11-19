package com.senijoshua.donezo.presentation.features.characters.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.senijoshua.donezo.presentation.components.EmptyState
import com.senijoshua.donezo.presentation.features.characters.CharactersUiEvent
import com.senijoshua.donezo.presentation.features.characters.CharactersViewModel
import com.senijoshua.donezo.presentation.features.characters.ListUiState
import com.senijoshua.donezo.presentation.model.Character
import com.senijoshua.donezo.presentation.model.characterPreview
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import com.senijoshua.donezo.presentation.utils.UiEventHandler
import com.senijoshua.donezo.presentation.utils.getGenericErrorMessage
import com.valentinilk.shimmer.shimmer
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
                    CharacterListLoadingScreen(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is ListUiState.Success -> {
                    val charactersList = state.characters

                    if (charactersList.isEmpty()) {
                        EmptyState(stringResource(Res.string.empty_character_list))
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            itemsIndexed(
                                items = charactersList,
                                key = { index, character -> character.id }
                            ) { index, character ->
                                CharacterItem(
                                    modifier = Modifier
                                        .clickable {
                                            onCharacterItemClicked(character.id)
                                        }
                                        .padding(vertical = MaterialTheme.dimensions.xSmall),
                                    character = character,
                                )

                                if (index != charactersList.lastIndex) {
                                    HorizontalDivider(
                                        thickness = MaterialTheme.dimensions.xxxSmall,
                                        color = MaterialTheme.colorScheme.outlineVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    UiEventHandler( eventFlow = uiEvent) { event ->
        when (event) {
            is CharactersUiEvent.Error -> {
                val message = event.message ?: genericErrorMessage
                snackBarHostState.showSnackbar(message)
            }
        }
    }
}

@Composable
private fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(MaterialTheme.dimensions.xxxLarge)
            .padding(horizontal = MaterialTheme.dimensions.small),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            modifier = Modifier
                .size(MaterialTheme.dimensions.xxLarge)
                .clip(CircleShape),
            model = character.thumbnailUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(start = MaterialTheme.dimensions.xSmall)
                .fillMaxWidth(),
        ) {
            Text(
                text = character.fullName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.padding(top = MaterialTheme.dimensions.xSmall),
                text = character.title,
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun CharacterListLoadingScreen(modifier: Modifier) {
    Column(modifier) {
        repeat(3, { index ->
            Row(
                modifier = Modifier
                    .shimmer()
                    .padding(
                        horizontal = MaterialTheme.dimensions.small,
                        vertical = MaterialTheme.dimensions.xSmall
                    )
                    .height(MaterialTheme.dimensions.xxxLarge),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(MaterialTheme.dimensions.xxLarge)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.outlineVariant)
                )

                Column(
                    modifier = Modifier
                        .padding(start = MaterialTheme.dimensions.xSmall)
                        .fillMaxWidth(),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MaterialTheme.dimensions.medium)
                            .clip(RoundedCornerShape(MaterialTheme.dimensions.xxSmall))
                            .background(color = MaterialTheme.colorScheme.outlineVariant)
                    )

                    Box(
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimensions.xSmall)
                            .size(
                                width = MaterialTheme.dimensions.custom112,
                                height = MaterialTheme.dimensions.medium
                            )
                            .clip(RoundedCornerShape(MaterialTheme.dimensions.xxSmall))
                            .background(color = MaterialTheme.colorScheme.outlineVariant)
                    )
                }
            }

            if (index != 2) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = MaterialTheme.dimensions.xxxSmall,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        })
    }
}

@Preview
@Composable
private fun CharactersItemLightPreview() {
    DonezoTheme {
        Surface {
            CharacterItem(
                character = characterPreview[0],
                modifier = Modifier.padding(MaterialTheme.dimensions.small),
            )
        }
    }
}

@Preview
@Composable
private fun CharactersItemDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
            CharacterItem(
                character = characterPreview[0],
                modifier = Modifier.padding(MaterialTheme.dimensions.small),
            )
        }
    }
}

@Preview
@Composable
private fun CharactersListLightPreview() {
    DonezoTheme {
        CharactersListContent(
            state = ListUiState.Loading,//.Success(characters = characterPreview),
            uiEvent = MutableSharedFlow()
        )
    }
}

@Preview
@Composable
private fun CharactersListDarkPreview() {
    DonezoTheme(darkTheme = true) {
        CharactersListContent(
            state = ListUiState.Success(characters = characterPreview),
            uiEvent = MutableSharedFlow()
        )
    }
}

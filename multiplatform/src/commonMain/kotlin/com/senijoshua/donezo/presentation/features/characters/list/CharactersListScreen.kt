package com.senijoshua.donezo.presentation.features.characters.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
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
import com.revenuecat.placeholder.PlaceholderDefaults
import com.revenuecat.placeholder.placeholder
import com.senijoshua.donezo.presentation.components.EmptyState
import com.senijoshua.donezo.presentation.features.characters.CharactersUiEvent
import com.senijoshua.donezo.presentation.features.characters.CharactersViewModel
import com.senijoshua.donezo.presentation.features.characters.ListUiState
import com.senijoshua.donezo.presentation.model.Character
import com.senijoshua.donezo.presentation.model.characterPreview
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import com.senijoshua.donezo.presentation.utils.getGenericErrorMessage
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.characters_header
import donezo.multiplatform.generated.resources.empty_character_list
import donezo.multiplatform.generated.resources.ic_account_circle
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.painterResource
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
                    LoadingScreen(modifier = Modifier.fillMaxSize())
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
                                        .clickable{
                                            onCharacterItemClicked(character.id)
                                        }
                                        .padding(vertical = MaterialTheme.dimensions.xSmall),
                                    character = character,
                                    isLoading = false,
                                )

                                if (index != charactersList.lastIndex) {
                                    HorizontalDivider(
                                        modifier = Modifier,
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
    isLoading: Boolean,
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
                .clip(CircleShape)
                .placeholder(
                    enabled = isLoading,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = CircleShape,
                    highlight = PlaceholderDefaults.shimmer
                )
            ,
            model = character.thumbnailUrl,
            placeholder = painterResource(Res.drawable.ic_account_circle),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier
            .padding(start = MaterialTheme.dimensions.xSmall)
            .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.placeholder(
                    enabled = isLoading,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(
                        MaterialTheme.dimensions.xxSmall
                    ),
                    highlight = PlaceholderDefaults.shimmer
                ),
                text = character.fullName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier.padding(top = MaterialTheme.dimensions.xSmall)
                    .placeholder(
                        enabled = isLoading,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(
                            MaterialTheme.dimensions.xxSmall
                        ),
                        highlight = PlaceholderDefaults.shimmer
                    ),
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
private fun LoadingScreen(modifier: Modifier) {
    Column(modifier) {
        repeat(3, { index ->
            CharacterItem(
                modifier = Modifier.padding(vertical = MaterialTheme.dimensions.xSmall),
                character = characterPreview[0],
                isLoading = true,
            )

            if (index != 2) {
                HorizontalDivider(
                    modifier = Modifier,
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
                isLoading = false,
            )
        }
    }
}

@Preview
@Composable
private fun CharactersItemDarkPreview() {
    DonezoTheme(darkTheme = true){
        Surface {
            CharacterItem(
                character = characterPreview[0],
                modifier = Modifier.padding(MaterialTheme.dimensions.small),
                isLoading = false,
            )
        }
    }
}

@Preview
@Composable
private fun CharactersListLightPreview() {
    DonezoTheme {
        CharactersListContent(
            state = ListUiState.Success(characters = characterPreview),
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

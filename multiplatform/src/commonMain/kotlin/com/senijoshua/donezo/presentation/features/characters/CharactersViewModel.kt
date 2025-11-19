package com.senijoshua.donezo.presentation.features.characters

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.senijoshua.donezo.data.repository.characters.CharactersRepository
import com.senijoshua.donezo.presentation.model.Character
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

/**
 * Shared ViewModel for the characters tab destinations
 */
class CharactersViewModel(
    private val savedStateHandle: SavedStateHandle,
    repository: CharactersRepository
) : ViewModel() {
    // Setup shared flow for one-time events like errors. Each screen would collect it independently as long as they are in the composition
    private val _uiEvent = MutableSharedFlow<CharactersUiEvent>(replay = 0)
    val uiEvent: SharedFlow<CharactersUiEvent> = _uiEvent

    // Setup observable data holding state flow that kicks off its work on collection from the characterlist UI
    val listUiState: StateFlow<ListUiState> = repository.getCharacters()
        .onEach { result ->
            if (result.isFailure) {
                val error = result.exceptionOrNull()
                _uiEvent.emit(CharactersUiEvent.Error(error?.message))
            }
        }.map { result ->
            ListUiState.Success(result.getOrNull().orEmpty())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ListUiState.Loading
        )

    // Setup a cold flow that kicks off its work on collection from the character detail UI
    // which then gets converted to a StateFlow i.e. a hot flow.
    val detailUiState: StateFlow<DetailUiState> = flow {
        val detailRoute: CharactersDetailRoute = savedStateHandle.toRoute()
        val result = repository.getCharacterGivenId(detailRoute.id)
        when {
            result.isSuccess -> {
                val data = result.getOrNull()!!
                emit(DetailUiState.Success(data))
            }
            result.isFailure -> {
                val error = result.exceptionOrNull()!!
                _uiEvent.emit(CharactersUiEvent.Error(error.message))
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DetailUiState.Loading
    )
}

/**
 * Representation of the characterList at each instant in time
 */
sealed interface ListUiState {
    data class Success(val characters: List<Character>) : ListUiState
    data object Loading : ListUiState
}

/**
 * Representation of the characterDetail at each instant in time
 */
sealed interface DetailUiState {
    data class Success(val character: Character) : DetailUiState
    data object Loading : DetailUiState
}

/**
 * Denotation of one-time ui events like error messages/navigation
 */
sealed interface CharactersUiEvent {
    data class Error(val message: String?) : CharactersUiEvent
}


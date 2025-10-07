package com.senijoshua.donezo.presentation.features.completed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senijoshua.donezo.data.repository.tasks.TasksRepository
import com.senijoshua.donezo.presentation.model.PresentationTask
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CompletedViewModel(private val repository: TasksRepository) : ViewModel() {
    // Setup a shared-flow for one-time events like an error or navigation event
    private val _uiEvent = MutableSharedFlow<CompletedUiEvent>(replay = 0, extraBufferCapacity = 1)
    val uiEvent: SharedFlow<CompletedUiEvent> = _uiEvent

    // Setup an observable data holder
    val uiState: StateFlow<CompletedUiState> = repository.getCompletedTasks()
        .onEach { result ->
            if (result.isFailure) {
                val error = result.exceptionOrNull()
                _uiEvent.emit(CompletedUiEvent.Error(error?.message))
            }
        }
        .map { result ->
            CompletedUiState.Success(completedTasks = result.getOrNull().orEmpty())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CompletedUiState.Loading
        )

    fun markTaskAsTodo(id: Int) {
        viewModelScope.launch {
            repository.toggleTaskCompleteStatus(id)
        }
    }

    fun deleteCompletedTask(id: Int) {
        viewModelScope.launch {
            repository.deleteTask(id)
        }
    }
}

/**
 * Representation of the UI state at any instant in time
 */
sealed interface CompletedUiState {
    data class Success(val completedTasks: List<PresentationTask>) : CompletedUiState
    data object Loading : CompletedUiState
}

/**
 * Denotation of one-time ui events like error messages/navigation
 */
sealed interface CompletedUiEvent {
    data class Error(val message: String?) : CompletedUiEvent
}

package com.senijoshua.donezo.presentation.tasks

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow

class TasksViewModel : ViewModel() {
    // Setup state holder flow for ui state

    // Setup shared flow for one-time UI events
    private val _uiEvent = MutableSharedFlow<TasksUIEvent>(
        replay = 0,
        extraBufferCapacity = 1 // to avoid dropping events when no collector is immediately ready.
    )
    val uiEvent: SharedFlow<TasksUIEvent> = _uiEvent

    // Setup cold flow to get Tasks from the DB
    val uiState = flow {
        try {
            // TODO Call get tasks from repository
            emit(TasksUIState.Success(tasks = emptyList()))
        } catch (e: Exception) {
            _uiEvent.emit(TasksUIEvent.Error(e.message))
        }
    }
    // Create, Update, Delete, and Mark Task as Completed operations
}

/**
 * Representation of the state of the UI at any instant in time.
 */
sealed interface TasksUIState {
    data class Success(val tasks: List<ToDoTasks>) : TasksUIState
    data object Loading : TasksUIState
}

/**
 * Denotation of the various UI events that occur in TasksScreen
 */
sealed interface TasksUIEvent {
    data class Error(val message: String?) : TasksUIEvent
}

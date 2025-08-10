package com.senijoshua.donezo.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senijoshua.donezo.presentation.tasks.model.TodoTasks
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {
    // Setup state holder flow for ui state

    // Setup shared flow for one-time UI events
    private val _uiEvent = MutableSharedFlow<TasksUIEvent>(
        replay = 0,
        extraBufferCapacity = 1 // to avoid dropping events when no collector is immediately ready.
    )
    val uiEvent: SharedFlow<TasksUIEvent> = _uiEvent

    // Setup cold flow to get Tasks from the DB
    val uiState: StateFlow<TasksUIState> = flow {
        try {
            // TODO Call get tasks from repository
            emit(TasksUIState.Success(tasks = emptyList()))
        } catch (e: Exception) {
            _uiEvent.emit(TasksUIEvent.Error(e.message))
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TasksUIState.Loading
    )
    // Create, Update, Delete, and Mark Task as Completed operations

    fun saveTask(taskDetails: Triple<String, String, String>) {
        // TODO Ideally we should sanitize the input to prevent
        //  security risks like SQL injection.
        viewModelScope.launch {

        }
    }
}

/**
 * Representation of the state of the UI at any instant in time.
 */
sealed interface TasksUIState {
    data class Success(val tasks: List<TodoTasks>) : TasksUIState
    data object Loading : TasksUIState
}

/**
 * Denotation of the various UI events that occur in TasksScreen
 */
sealed interface TasksUIEvent {
    data class Error(val message: String?) : TasksUIEvent
}

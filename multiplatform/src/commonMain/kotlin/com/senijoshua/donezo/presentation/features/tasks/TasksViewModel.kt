package com.senijoshua.donezo.presentation.features.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senijoshua.donezo.data.repository.tasks.TasksRepository
import com.senijoshua.donezo.presentation.model.PresentationTask
import com.senijoshua.donezo.presentation.model.TaskUpdateDetails
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TasksViewModel(private val repository: TasksRepository) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<TasksUIEvent>(
        replay = 0,
        extraBufferCapacity = 1 // to avoid dropping events when no collector is immediately ready.
    )
    val uiEvent: SharedFlow<TasksUIEvent> = _uiEvent

    // Idiomatic way to kick-off streaming (or observance) of UI state changes
    // when the UI initialises collection and re-collection.
   // When re-collected, Room just delivers the latest cached snapshot,
    // not a fresh query unless data actually changed.
    val uiState: StateFlow<TasksUIState> = repository.getTasks()
        .onEach { result ->
            if (result.isFailure) {
                // Emit an error UI event as a side-effect of collecting this "getTasks" flow
                // from within the viewModelScope coroutine scope.
                val error = result.exceptionOrNull()
                _uiEvent.emit(TasksUIEvent.Error(error?.message))
            }
        }
        .map { result ->
            if (result.isSuccess) {
                // Transforms result i.e. Flow<Result<List<PresentationTask>>>
                // to UI State i.e. Flow<TaskUIState> if successful
                val tasks = result.getOrNull().orEmpty()
                TasksUIState.Success(tasks = tasks)
            } else {
                TasksUIState.Success(tasks = emptyList())
            }
        }.stateIn( // converts Flow<TaskUIState> to StateFlow<TaskUIState>
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TasksUIState.Loading
        )

    // Create, Update, Delete, and Mark Task as Completed operations

    fun saveTask(taskUpdateDetails: TaskUpdateDetails, isNewTask: Boolean) {
        // TODO Ideally we should sanitize the input to prevent
        //  security risks like SQL injection.
        viewModelScope.launch {
            if (isNewTask) {
                repository.createTask(title = taskUpdateDetails.title, description = taskUpdateDetails.description)
            } else {
                repository.updateTask(id = taskUpdateDetails.id, title = taskUpdateDetails.title, description = taskUpdateDetails.description)
            }
        }
    }

    fun markTaskAsCompleted(taskId: Int) {
        viewModelScope.launch {
            repository.toggleTaskCompleteStatus(taskId)
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }
}

/**
 * Representation of the state of the UI at any instant in time.
 */
sealed interface TasksUIState {
    data class Success(val tasks: List<PresentationTask>) : TasksUIState
    data object Loading : TasksUIState
}

/**
 * Denotation of the various UI events that occur in TasksScreen
 */
sealed interface TasksUIEvent {
    data class Error(val message: String?) : TasksUIEvent
}

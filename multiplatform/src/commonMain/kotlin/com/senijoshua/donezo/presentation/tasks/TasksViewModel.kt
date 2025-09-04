package com.senijoshua.donezo.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senijoshua.donezo.data.repository.TasksRepository
import com.senijoshua.donezo.presentation.tasks.model.PresentationTask
import com.senijoshua.donezo.presentation.tasks.model.TaskUpdateDetails
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TasksViewModel(private val repository: TasksRepository) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<TasksUIEvent>(
        replay = 0,
        extraBufferCapacity = 1 // to avoid dropping events when no collector is immediately ready.
    )
    val uiEvent: SharedFlow<TasksUIEvent> = _uiEvent

    val uiState: StateFlow<TasksUIState> = flow {
        repository.getTasks().collectLatest { result ->
            when {
                result.isSuccess -> {
                    val tasks = result.getOrNull()!!
                    emit(TasksUIState.Success(tasks = tasks))
                }

                result.isFailure -> {
                    val error = result.exceptionOrNull()!!
                    _uiEvent.emit(TasksUIEvent.Error(error.message))
                }
            }
        }
    }.stateIn(
        viewModelScope,
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

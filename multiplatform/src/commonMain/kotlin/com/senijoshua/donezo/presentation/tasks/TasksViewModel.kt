package com.senijoshua.donezo.presentation.tasks

import androidx.lifecycle.ViewModel

class TasksViewModel : ViewModel() {
    // Setup state holder flow for ui state

    // Setup shared flow for one-time UI events

    // Setup cold flow to get Tasks from the DB

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
    data class Error(val message: String) : TasksUIEvent
}

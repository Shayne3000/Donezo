@file:OptIn(ExperimentalMaterial3Api::class)

package com.senijoshua.donezo.presentation.features.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.senijoshua.donezo.presentation.components.EmptyState
import com.senijoshua.donezo.presentation.components.LoadingScreen
import com.senijoshua.donezo.presentation.components.TaskItem
import com.senijoshua.donezo.presentation.features.tasks.components.bottomsheet.TasksBottomSheet
import com.senijoshua.donezo.presentation.model.PresentationTask
import com.senijoshua.donezo.presentation.model.TaskUpdateDetails
import com.senijoshua.donezo.presentation.model.tasksPreview
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import com.senijoshua.donezo.presentation.utils.getGenericErrorMessage
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.empty_state_todo_text
import donezo.multiplatform.generated.resources.ic_add
import donezo.multiplatform.generated.resources.tasks_tab_title
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TasksScreen(
    viewModel: TasksViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val genericErrorMessage = getGenericErrorMessage()

    TasksContent(
        uiState = uiState,
        snackBarHostState = snackBarHostState,
        onSaveTask = { details, isNewTask ->
            viewModel.saveTask(details, isNewTask)
        },
        onTaskCompleted = { taskId ->
            viewModel.markTaskAsCompleted(taskId)
        },
        onDeleteTask = { taskId ->
            viewModel.deleteTask(taskId)
        }
    )

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { events ->
            when (events) {
                is TasksUIEvent.Error -> {
                    val message = events.message ?: genericErrorMessage
                    snackBarHostState.showSnackbar(message)
                }
            }
        }
    }
}

@Composable
private fun TasksContent(
    uiState: TasksUIState,
    snackBarHostState: SnackbarHostState,
    onSaveTask: (TaskUpdateDetails, Boolean) -> Unit,
    onDeleteTask: (Int) -> Unit,
    onTaskCompleted: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showTaskDialog by mutableStateOf(false)
    val sheetState = rememberModalBottomSheetState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var taskBottomSheetMode by mutableStateOf(TaskBottomSheetMode.CREATE)
    var hasCreatedNewItem by remember { mutableStateOf(false) }
    var selectedTask: PresentationTask? = null

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                onClick = {
                    taskBottomSheetMode = TaskBottomSheetMode.CREATE
                    showTaskDialog = true
                }
            ) {
                Icon(painter = painterResource(Res.drawable.ic_add), contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize().background(color = MaterialTheme.colorScheme.surface)
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.dimensions.small,
                        top = MaterialTheme.dimensions.small
                    )
                    .fillMaxWidth(),
                text = stringResource(Res.string.tasks_tab_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            when (uiState) {
                is TasksUIState.Loading -> {
                    LoadingScreen(
                        modifier = Modifier.fillMaxSize()
                            .padding(horizontal = MaterialTheme.dimensions.small)
                    )
                }

                is TasksUIState.Success -> {
                    val tasks = uiState.tasks

                    LaunchedEffect(tasks.size) {
                        if (tasks.isNotEmpty() && hasCreatedNewItem) {
                            listState.scrollToItem(tasks.lastIndex)
                            hasCreatedNewItem = false
                        }
                    }

                    if (tasks.isEmpty()) {
                        EmptyState(stringResource(Res.string.empty_state_todo_text))
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            state = listState,
                            contentPadding = PaddingValues(horizontal = MaterialTheme.dimensions.small)
                        ) {
                            items(items = tasks, key = { task -> task.id }) { task ->
                                TaskItem(
                                    modifier = Modifier
                                        .padding(vertical = MaterialTheme.dimensions.xSmall)
                                        .animateItem(),
                                    task = task,
                                    isCompleted = false,
                                    onMarkedAsDone = {
                                        onTaskCompleted(task.id)
                                    },
                                    onEdit = { task ->
                                        taskBottomSheetMode = TaskBottomSheetMode.EDIT
                                        selectedTask = task
                                        showTaskDialog = true
                                    },
                                    onDelete = {
                                        onDeleteTask(task.id)
                                    },
                                    onClick = {
                                        taskBottomSheetMode = TaskBottomSheetMode.VIEW
                                        selectedTask = task
                                        showTaskDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showTaskDialog) {
            TasksBottomSheet(
                selectedTask = selectedTask,
                taskBottomSheetMode = taskBottomSheetMode,
                taskBottomSheetState = sheetState,
                onChangeBottomSheetMode = { newMode ->
                    taskBottomSheetMode = newMode
                },
                onSaveTask = { taskUpdate, isNewTask ->
                    onSaveTask(
                        TaskUpdateDetails(
                            taskUpdate.id,
                            taskUpdate.title,
                            taskUpdate.description
                        ),
                        isNewTask
                    )

                    if (isNewTask) {
                        hasCreatedNewItem = true
                    }
                },
                onDismiss = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        showTaskDialog = false
                    }
                }
            )
        }
    }
}

internal enum class TaskBottomSheetMode {
    CREATE,
    VIEW,
    EDIT
}

@Preview
@Composable
private fun TasksScreenLightPreview() {
    DonezoTheme {
        TasksContent(
            uiState = TasksUIState.Success(tasks = tasksPreview),
            onSaveTask = { _, _ -> },
            snackBarHostState = remember { SnackbarHostState() },
            onDeleteTask = {},
            onTaskCompleted = {}
        )
    }
}

@Preview
@Composable
private fun TasksScreenDarkPreview() {
    DonezoTheme(darkTheme = true) {
        TasksContent(
            uiState = TasksUIState.Success(tasks = tasksPreview),
            onSaveTask = { _, _ -> },
            snackBarHostState = remember { SnackbarHostState() },
            onDeleteTask = {},
            onTaskCompleted = {}
        )
    }
}

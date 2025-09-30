@file:OptIn(ExperimentalMaterial3Api::class)

package com.senijoshua.donezo.presentation.features.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.senijoshua.donezo.presentation.components.EmptyState
import com.senijoshua.donezo.presentation.components.LoadingScreen
import com.senijoshua.donezo.presentation.features.tasks.components.bottomsheet.TasksBottomSheet
import com.senijoshua.donezo.presentation.model.PresentationTask
import com.senijoshua.donezo.presentation.model.TaskUpdateDetails
import com.senijoshua.donezo.presentation.model.previewTasks
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import com.senijoshua.donezo.presentation.utils.getDateFormat
import com.senijoshua.donezo.presentation.utils.getGenericErrorMessage
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.complete_task_text
import donezo.multiplatform.generated.resources.empty_state_todo_text
import donezo.multiplatform.generated.resources.ic_add
import donezo.multiplatform.generated.resources.ic_completed
import donezo.multiplatform.generated.resources.ic_date
import donezo.multiplatform.generated.resources.ic_delete
import donezo.multiplatform.generated.resources.ic_edit
import donezo.multiplatform.generated.resources.task_header
import kotlinx.coroutines.launch
import kotlinx.datetime.format
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
    var hasCreatedNewItem by remember { mutableStateOf(false)}
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
                modifier = Modifier.padding(
                    start = MaterialTheme.dimensions.small,
                    top = MaterialTheme.dimensions.small
                ).fillMaxWidth(),
                text = stringResource(Res.string.task_header),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            when (uiState) {
                is TasksUIState.Loading -> {
                    LoadingScreen(
                        modifier = Modifier.fillMaxSize().padding(horizontal = MaterialTheme.dimensions.small)
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

@Composable
private fun TaskItem(
    task: PresentationTask,
    onMarkedAsDone: () -> Unit,
    onEdit: (PresentationTask) -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.dimensions.xxSmall)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(MaterialTheme.dimensions.xSmall))
                .clickable { onClick() }
                .padding(horizontal = MaterialTheme.dimensions.small)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(MaterialTheme.dimensions.xLarge),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_date),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(start = MaterialTheme.dimensions.xxSmall),
                    text = task.createdTime.format(getDateFormat()),
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    modifier = Modifier.offset(x = MaterialTheme.dimensions.xSmall),
                    onClick = {
                        onEdit(task)
                    }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_edit),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = MaterialTheme.dimensions.xxSmall),
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(top = MaterialTheme.dimensions.xSmall),
                text = task.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(
                    top = MaterialTheme.dimensions.small,
                    bottom = MaterialTheme.dimensions.xSmall
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    modifier = Modifier.offset(x = -(MaterialTheme.dimensions.xSmall)),
                    onClick = {
                        onDelete()
                    }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_delete),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                Button(
                    modifier = Modifier.wrapContentSize(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(CornerSize(MaterialTheme.dimensions.xxSmall)),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.dimensions.small),
                    onClick = {
                        onMarkedAsDone()
                    }) {
                    Icon(
                        modifier = Modifier.padding(end = MaterialTheme.dimensions.xxSmall),
                        painter = painterResource(Res.drawable.ic_completed),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(Res.string.complete_task_text),
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
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
private fun TaskItemLightPreview() {
    DonezoTheme {
        Surface {
            TaskItem(
                modifier = Modifier.padding(MaterialTheme.dimensions.small),
                task = previewTasks[0],
                onMarkedAsDone = {},
                onEdit = { },
                onDelete = {},
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun TaskItemDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
            TaskItem(
                modifier = Modifier.padding(16.dp),
                task = previewTasks[0],
                onMarkedAsDone = {},
                onEdit = { },
                onDelete = {},
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun TasksScreenLightPreview() {
    DonezoTheme {
        TasksContent(
            uiState = TasksUIState.Success(tasks = previewTasks),
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
            uiState = TasksUIState.Success(tasks = previewTasks),
            onSaveTask = { _, _ -> },
            snackBarHostState = remember { SnackbarHostState() },
            onDeleteTask = {},
            onTaskCompleted = {}
        )
    }
}

@file:OptIn(ExperimentalMaterial3Api::class)

package com.senijoshua.donezo.presentation.tasks

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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.senijoshua.donezo.presentation.components.EmptyState
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.complete_task_text
import donezo.multiplatform.generated.resources.empty_state_text
import donezo.multiplatform.generated.resources.generic_error_message
import donezo.multiplatform.generated.resources.ic_add
import donezo.multiplatform.generated.resources.ic_completed
import donezo.multiplatform.generated.resources.ic_date
import donezo.multiplatform.generated.resources.ic_delete
import donezo.multiplatform.generated.resources.ic_edit
import donezo.multiplatform.generated.resources.task_bottom_sheet_title
import donezo.multiplatform.generated.resources.task_bottom_sheet_title_placeholder
import donezo.multiplatform.generated.resources.tasks_tab_title
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TasksScreen(
    viewModel: TasksViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(initialValue = TasksUIState.Loading)
    val snackBarHostState = remember { SnackbarHostState() }
    val genericErrorMessage = stringResource(Res.string.generic_error_message)

    TasksContent(uiState = uiState, snackBarHostState = snackBarHostState)

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
    modifier: Modifier = Modifier
) {
    var showTaskDialog by mutableStateOf(false)
    val sheetState = rememberModalBottomSheetState()
    var taskBottomSheetMode by mutableStateOf(TaskBottomSheetMode.CREATE)

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
                text = stringResource(Res.string.tasks_tab_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            when (uiState) {
                is TasksUIState.Loading -> {}
                is TasksUIState.Success -> {
                    if (uiState.tasks.isEmpty()) {
                        EmptyState(stringResource(Res.string.empty_state_text))
                    } else {
                        LazyColumn(
                            Modifier
                                .padding(MaterialTheme.dimensions.xxSmall).fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = MaterialTheme.dimensions.small)
                        ) {
                            items(items = uiState.tasks, key = { task -> task.id }) { task ->
                                TaskItem(
                                    modifier = Modifier
                                        .padding(vertical = MaterialTheme.dimensions.xSmall)
                                        .animateItem(),
                                    task = task,
                                    onMarkedAsDone = {
                                        // animate item away
                                    },
                                    onEdit = { title, description ->
                                        taskBottomSheetMode = TaskBottomSheetMode.EDIT
                                        showTaskDialog = true
                                    },
                                    onDelete = {
                                        // TODO Show delete confirmation dialog
                                    },
                                    onClick = {
                                        taskBottomSheetMode = TaskBottomSheetMode.VIEW
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
                taskBottomSheetMode = taskBottomSheetMode,
                taskBottomSheetState = sheetState,
                onChangeBottomSheetMode = { newMode ->
                    taskBottomSheetMode = newMode
                },
                onDismiss = {
                    showTaskDialog = false
                }
            )
        }
    }
}

@Composable
private fun TaskItem(
    task: TodoTasks,
    onMarkedAsDone: () -> Unit,
    onEdit: (String, String) -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth().wrapContentHeight().clickable {
            onClick()
        },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.dimensions.xxSmall)
    ) {
        Column(modifier = Modifier.padding(horizontal = MaterialTheme.dimensions.small)) {
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
                        onEdit(task.title, task.description)
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

@Composable
private fun TasksBottomSheet(
    taskBottomSheetMode: TaskBottomSheetMode,
    onDismiss: () -> Unit,
    onChangeBottomSheetMode: (TaskBottomSheetMode) -> Unit,
    modifier: Modifier = Modifier,
    taskBottomSheetState: SheetState = rememberModalBottomSheetState(),
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = taskBottomSheetState,
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        dragHandle = null
    ) {
        var title by mutableStateOf(TextFieldValue(""))
        var description by mutableStateOf("")

        when (taskBottomSheetMode) {
            TaskBottomSheetMode.CREATE -> {
                Column(modifier = Modifier) {
                Column(modifier = Modifier.padding(MaterialTheme.dimensions.small)) {
                    Text(
                        text = stringResource(Res.string.task_bottom_sheet_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    // TODO Text New Task, with textfields for title and description (the text in
                    //  the field scrolls when full) with a save button at the top right
                }
            }

            TaskBottomSheetMode.VIEW -> {
                // TODO Task title, Task description (with the ability to scroll to see more),
                //  at the top right an edit button and at the bottom a delete button.
                //  Edit button animates out and changes the mode to edit
            }

            TaskBottomSheetMode.EDIT -> {
                // TODO Task title textfield with task title, task description textfield with task description,
                //  A save button at the top rgiht
            }
        }
    }
}

private fun getDateFormat(): DateTimeFormat<LocalDate> {
    return LocalDate.Format {
        day()
        char(' ')
        monthName(names = MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        year()
    }
}

private enum class TaskBottomSheetMode {
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
                onEdit = { _, _ -> },
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
                onEdit = { _, _ -> },
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
            snackBarHostState = remember { SnackbarHostState() }
        )
    }
}

@Preview
@Composable
private fun TasksScreenDarkPreview() {
    DonezoTheme(darkTheme = true) {
        TasksContent(
            uiState = TasksUIState.Success(tasks = previewTasks),
            snackBarHostState = remember { SnackbarHostState() }
        )
    }
}

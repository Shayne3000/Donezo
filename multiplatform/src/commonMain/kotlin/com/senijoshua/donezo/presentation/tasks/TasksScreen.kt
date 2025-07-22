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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun TasksContent(
    uiState: TasksUIState,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    var showCreateTaskDialog by mutableStateOf(false)

    if (showCreateTaskDialog) {
        // TODO Show create task bottom sheet
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                onClick = {
                    showCreateTaskDialog = true
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
                            Modifier.padding(MaterialTheme.dimensions.xxSmall).fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = MaterialTheme.dimensions.small)
                        ) {
                            items(items = uiState.tasks, key = { task -> task.id }) { task ->
                                TaskItem(
                                    modifier = Modifier.padding(vertical = MaterialTheme.dimensions.xSmall),
                                    task = task,
                                    onMarkedAsDone = {

                                    },
                                    onEdit = { title, description ->
                                        showCreateTaskDialog = true
                                             },
                                    onDelete = {
                                        // TODO Show delete confirmation dialog
                                    },
                                    onClick = {
                                        showCreateTaskDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(
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
            Row(modifier = Modifier.fillMaxWidth().height(MaterialTheme.dimensions.xLarge), verticalAlignment = Alignment.CenterVertically) {
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
                // TODO Use offset or graphic layer to move it right beyond its bounds
                IconButton(
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
            Row(modifier = Modifier.fillMaxWidth().padding(top = MaterialTheme.dimensions.small, bottom = MaterialTheme.dimensions.xSmall), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                // TODO Use offset or graphic layer to move it left beyond its bounds
                IconButton(
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

private fun getDateFormat(): DateTimeFormat<LocalDate> {
    return LocalDate.Format {
        day()
        char( ' ')
        monthName(names = MonthNames.ENGLISH_ABBREVIATED)
        char( ' ')
        year()
    }
}

@Composable
private fun LoadingShimmer() {
    // TODO Tasks loading shimmer after determining how the view would look.
}

@Preview
@Composable
fun TaskItemLightPreview() {
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
fun TaskItemDarkPreview() {
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
fun TasksScreenLightPreview() {
    DonezoTheme {
        TasksContent(
            uiState = TasksUIState.Success(tasks = previewTasks),
            snackBarHostState = remember { SnackbarHostState() }
        )
    }
}

@Preview
@Composable
fun TasksScreenDarkPreview() {
    DonezoTheme(darkTheme = true) {
        TasksContent(
            uiState = TasksUIState.Success(tasks = previewTasks),
            snackBarHostState = remember { SnackbarHostState() }
        )
    }
}

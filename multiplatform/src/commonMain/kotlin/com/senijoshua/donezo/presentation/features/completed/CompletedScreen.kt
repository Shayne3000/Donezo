package com.senijoshua.donezo.presentation.features.completed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.senijoshua.donezo.presentation.components.EmptyState
import com.senijoshua.donezo.presentation.components.LoadingScreen
import com.senijoshua.donezo.presentation.components.TaskItem
import com.senijoshua.donezo.presentation.model.previewTasks
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import com.senijoshua.donezo.presentation.utils.getGenericErrorMessage
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.completed_header
import donezo.multiplatform.generated.resources.empty_state_completed_text
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CompletedScreen(
    viewModel: CompletedViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiEvent = viewModel.uiEvent

    CompletedContent(
        uiState = uiState,
        uiEvent = uiEvent,
        onMarkedAsTodo = viewModel::markTaskAsTodo,
        onDelete = viewModel::deleteCompletedTask
    )
}

@Composable
private fun CompletedContent(
    modifier: Modifier = Modifier,
    uiState: CompletedUiState,
    uiEvent: SharedFlow<CompletedUiEvent>,
    onMarkedAsTodo: (Int) -> Unit,
    onDelete: (Int) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val genericErrorMessage = getGenericErrorMessage()

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) { data ->
            Snackbar(
                snackbarData = data,
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        }
    }) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(paddingValues)
        ) {
            Text(
                modifier = Modifier.padding(
                    start = MaterialTheme.dimensions.small,
                    top = MaterialTheme.dimensions.small
                ).fillMaxWidth(),
                text = stringResource(Res.string.completed_header),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            when (uiState) {
                is CompletedUiState.Loading -> {
                    LoadingScreen(
                        modifier = Modifier.fillMaxSize()
                            .padding(horizontal = MaterialTheme.dimensions.small),
                        shouldShowEditButton = false
                    )
                }

                is CompletedUiState.Success -> {
                    val completedTasks = uiState.completedTasks

                    if (completedTasks.isEmpty()) {
                        EmptyState(stringResource(Res.string.empty_state_completed_text))
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = MaterialTheme.dimensions.small)
                        ) {
                            items(
                                items = completedTasks,
                                key = { task -> task.id }) { completedTasks ->
                                TaskItem(
                                    modifier = Modifier
                                        .padding(vertical = MaterialTheme.dimensions.xSmall)
                                        .animateItem(),
                                    task = completedTasks,
                                    onMarkedAsTodo = {
                                        onMarkedAsTodo(completedTasks.id)
                                    },
                                    isCompleted = true,
                                    onDelete = {
                                        onDelete(completedTasks.id)
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        uiEvent.collect { events ->
            when (events) {
                is CompletedUiEvent.Error -> {
                    val message = events.message ?: genericErrorMessage
                    snackBarHostState.showSnackbar(message)
                }
            }
        }
    }
}

@Preview
@Composable
private fun CompletedTaskItemLightPreview() {
    DonezoTheme {
        Surface {
            TaskItem(
                modifier = Modifier.padding(MaterialTheme.dimensions.small),
                task = previewTasks[0],
                isCompleted = true,
                onDelete = {},
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun CompletedTaskItemDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
            TaskItem(
                modifier = Modifier.padding(MaterialTheme.dimensions.small),
                task = previewTasks[0],
                isCompleted = true,
                onDelete = {},
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun CompletedScreenLightPreview() {
    DonezoTheme {
        Surface {
            CompletedContent(
                uiState = CompletedUiState.Success(completedTasks = previewTasks),
                uiEvent = MutableSharedFlow(),
                onMarkedAsTodo = {},
                onDelete = {},
            )
        }
    }
}

@Preview
@Composable
private fun CompletedScreenDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
            CompletedContent(
                uiState = CompletedUiState.Success(completedTasks = previewTasks),
                uiEvent = MutableSharedFlow(),
                onMarkedAsTodo = {},
                onDelete = {},
            )
        }
    }
}

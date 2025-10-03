package com.senijoshua.donezo.presentation.features.completed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.senijoshua.donezo.presentation.components.EmptyState
import com.senijoshua.donezo.presentation.components.LoadingScreen
import com.senijoshua.donezo.presentation.model.PresentationTask
import com.senijoshua.donezo.presentation.model.previewTasks
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import com.senijoshua.donezo.presentation.utils.getDateFormat
import com.senijoshua.donezo.presentation.utils.getGenericErrorMessage
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.completed_header
import donezo.multiplatform.generated.resources.empty_state_completed_text
import donezo.multiplatform.generated.resources.ic_date
import donezo.multiplatform.generated.resources.ic_delete
import donezo.multiplatform.generated.resources.ic_tasks
import donezo.multiplatform.generated.resources.todo_task_text
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.datetime.format
import org.jetbrains.compose.resources.painterResource
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
        uiEvent = uiEvent
    )
}

@Composable
private fun CompletedContent(
    modifier: Modifier = Modifier,
    uiState: CompletedUiState,
    uiEvent: SharedFlow<CompletedUiEvent>
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
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(
                                items = completedTasks,
                                key = { task -> task.id }) { completedTasks ->
                                CompletedTaskItem(
                                    modifier = Modifier
                                        .padding(vertical = MaterialTheme.dimensions.xSmall)
                                        .animateItem(),
                                    completedTask = completedTasks,
                                    onMarkedAsTodo = {},
                                    onDelete = {},
                                    onClick = {}
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

@Composable
private fun CompletedTaskItem(
    completedTask: PresentationTask,
    onMarkedAsTodo: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // TODO Move this out into the components directory.
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
                modifier = Modifier.fillMaxWidth().height(MaterialTheme.dimensions.xLarge)
                    .alpha(0.5f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_date),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(start = MaterialTheme.dimensions.xxSmall),
                    text = completedTask.createdTime.format(getDateFormat()),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Text(
                modifier = Modifier.padding(top = MaterialTheme.dimensions.xxSmall).alpha(0.5f),
                text = completedTask.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(top = MaterialTheme.dimensions.xSmall).alpha(0.5f),
                text = completedTask.description,
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
                    onClick = onDelete
                ) {
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
                    onClick = onMarkedAsTodo
                ) {
                    Icon(
                        modifier = Modifier.padding(end = MaterialTheme.dimensions.xxSmall),
                        painter = painterResource(Res.drawable.ic_tasks),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(Res.string.todo_task_text),
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskItemLightPreview() {
    DonezoTheme {
        Surface {
            CompletedTaskItem(
                modifier = Modifier.padding(MaterialTheme.dimensions.small),
                completedTask = previewTasks[0],
                onMarkedAsTodo = {},
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
            CompletedTaskItem(
                modifier = Modifier.padding(MaterialTheme.dimensions.small),
                completedTask = previewTasks[0],
                onMarkedAsTodo = {},
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
                uiState = CompletedUiState.Loading,
                uiEvent = MutableSharedFlow()
            )
        }
    }
}

@Preview
@Composable
private fun CompletedScreenDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
            CompletedContent(uiState = CompletedUiState.Loading, uiEvent = MutableSharedFlow())
        }
    }
}

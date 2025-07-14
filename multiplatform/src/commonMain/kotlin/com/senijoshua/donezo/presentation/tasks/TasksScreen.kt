package com.senijoshua.donezo.presentation.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.generic_error_message
import donezo.multiplatform.generated.resources.ic_add
import donezo.multiplatform.generated.resources.tasks_tab_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
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
                    start = DonezoTheme.dimensions.medium,
                    top = DonezoTheme.dimensions.medium
                ),
                text = stringResource(Res.string.tasks_tab_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            // TODO Tasks list
        }
    }
}

@Composable
private fun LoadingShimmer() {
    // TODO Tasks loading shimmer after determining how the view would look.
}

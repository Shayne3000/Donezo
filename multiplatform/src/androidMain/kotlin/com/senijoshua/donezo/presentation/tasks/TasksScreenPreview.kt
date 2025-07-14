package com.senijoshua.donezo.presentation.tasks

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.senijoshua.donezo.presentation.theme.DonezoTheme

@PreviewLightDark
@Composable
fun TasksScreenPreview() {
    DonezoTheme {
        TasksContent(
            uiState = TasksUIState.Loading,
            snackBarHostState = remember{ SnackbarHostState() }
        )
    }
}

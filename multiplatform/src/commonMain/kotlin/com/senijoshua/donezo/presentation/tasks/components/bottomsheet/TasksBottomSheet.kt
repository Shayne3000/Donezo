package com.senijoshua.donezo.presentation.tasks.components.bottomsheet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.senijoshua.donezo.presentation.tasks.TaskBottomSheetMode
import com.senijoshua.donezo.presentation.tasks.model.TodoTasks
import com.senijoshua.donezo.presentation.theme.dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TasksBottomSheet(
    selectedTask: TodoTasks?,
    taskBottomSheetMode: TaskBottomSheetMode,
    onDismiss: () -> Unit,
    onChangeBottomSheetMode: (TaskBottomSheetMode) -> Unit,
    onSaveTask: () -> Unit,
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
        when (taskBottomSheetMode) {
            TaskBottomSheetMode.CREATE -> {
                TaskBottomSheetCreateContent(
                    modifier = Modifier.padding(MaterialTheme.dimensions.small),
                    onSaveTask = {
                        onDismiss()
                        onSaveTask()
                    }
                )
            }

            TaskBottomSheetMode.VIEW -> {
                // TODO Task title, Task description (with the ability to scroll to see more),
                //  at the top right an edit button and at the bottom a delete button.
                //  Edit button animates out and changes the mode to edit
                selectedTask?.let { task ->
                    TaskBottomSheetViewContent(
                        selectedTask = task,
                        onEditClicked = {
                            onChangeBottomSheetMode(TaskBottomSheetMode.EDIT)
                        }
                    )
                }
            }

            TaskBottomSheetMode.EDIT -> {
                // TODO Task title textfield with task title, task description textfield with task description,
                //  A save button at the top rgiht
            }
        }
    }
}

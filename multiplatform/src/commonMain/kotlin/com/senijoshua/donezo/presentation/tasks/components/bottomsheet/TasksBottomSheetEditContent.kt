package com.senijoshua.donezo.presentation.tasks.components.bottomsheet

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.senijoshua.donezo.presentation.tasks.model.TaskUpdateDetails
import com.senijoshua.donezo.presentation.tasks.model.TodoTask
import com.senijoshua.donezo.presentation.tasks.model.previewTasks
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.edit_task
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun TasksBottomSheetEditContent(
    selectedTask: TodoTask,
    onSaveTask: (TaskUpdateDetails, isNewTask: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf(TextFieldValue(selectedTask.title)) }
    var description by remember { mutableStateOf(TextFieldValue(selectedTask.description)) }

    TaskBottomSheetEditableContent(
        modifier = modifier,
        contentTitle = stringResource(Res.string.edit_task),
        title = title,
        description = description,
        onTitleChanged =  { newTextFieldValue ->
            title = newTextFieldValue
        },
        onDescriptionChanged = { newTextFieldValue ->
            description = newTextFieldValue
        },
        onSaveTask = { (title, description) ->
            onSaveTask(TaskUpdateDetails(id = selectedTask.id, title = title, description = description), false)
        }
    )
}

@Preview
@Composable
private fun TaskBottomSheetEditContentLightPreview() {
    DonezoTheme {
        Surface {
            TasksBottomSheetEditContent(
                modifier = Modifier.padding(MaterialTheme.dimensions.small),
                selectedTask = previewTasks[0],
                onSaveTask = {_,_ ->},
            )
        }
    }
}

@Preview
@Composable
private fun TaskBottomSheetEditContentDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
            TasksBottomSheetEditContent(
                modifier = Modifier.padding(MaterialTheme.dimensions.small),
                selectedTask = previewTasks[0],
                onSaveTask = {_,_ ->},
            )
        }
    }
}

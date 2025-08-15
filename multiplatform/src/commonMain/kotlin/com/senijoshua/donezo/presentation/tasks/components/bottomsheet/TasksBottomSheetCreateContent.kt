package com.senijoshua.donezo.presentation.tasks.components.bottomsheet

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.senijoshua.donezo.presentation.tasks.model.TaskUpdateDetails
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.new_task
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun TasksBottomSheetCreateContent(
    modifier: Modifier = Modifier,
    onSaveTask: (TaskUpdateDetails, isNewTask: Boolean) -> Unit,
) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }

    TaskBottomSheetEditableContent(
        modifier = modifier,
        contentTitle = stringResource(Res.string.new_task),
        title = title,
        description = description,
        onTitleChanged =  { newTextFieldValue ->
            title = newTextFieldValue
        },
        onDescriptionChanged = { newTextFieldValue ->
            description = newTextFieldValue
        },
        onSaveTask = { (title, description) ->
            onSaveTask(TaskUpdateDetails(id = "", title = title, description = description), true)
        }
    )
}

@Preview
@Composable
private fun TasksBottomSheetCreateContentLightPreview() {
    DonezoTheme {
        Surface {
            TasksBottomSheetCreateContent(
                onSaveTask = {_,_ ->},
            )
        }
    }
}

@Preview
@Composable
private fun TasksBottomSheetCreateContentDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
            TasksBottomSheetCreateContent(
                onSaveTask = {_,_ ->},
            )
        }
    }
}

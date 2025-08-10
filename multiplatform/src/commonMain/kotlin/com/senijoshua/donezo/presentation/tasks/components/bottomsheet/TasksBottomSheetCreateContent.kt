package com.senijoshua.donezo.presentation.tasks.components.bottomsheet

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.new_task
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TasksBottomSheetCreateContent(
    modifier: Modifier = Modifier,
    onSaveTask: (Pair<String, String>) -> Unit,
) {
    var title by mutableStateOf(TextFieldValue(""))
    var description by mutableStateOf(TextFieldValue(""))

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
        onSaveTask = onSaveTask
    )
}

@Preview
@Composable
private fun TasksBottomSheetCreateContentLightPreview() {
    DonezoTheme {
        Surface {
            TasksBottomSheetCreateContent(
                onSaveTask = {},
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
                onSaveTask = {},
            )
        }
    }
}

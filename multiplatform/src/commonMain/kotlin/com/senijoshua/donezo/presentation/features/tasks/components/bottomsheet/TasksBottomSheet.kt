package com.senijoshua.donezo.presentation.features.tasks.components.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import com.senijoshua.donezo.presentation.components.DonezoTextField
import com.senijoshua.donezo.presentation.features.tasks.TaskBottomSheetMode
import com.senijoshua.donezo.presentation.model.Task
import com.senijoshua.donezo.presentation.model.TaskUpdateDetails
import com.senijoshua.donezo.presentation.theme.dimensions
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.description_placeholder
import donezo.multiplatform.generated.resources.save
import donezo.multiplatform.generated.resources.title_placeholder
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TasksBottomSheet(
    selectedTask: Task?,
    taskBottomSheetMode: TaskBottomSheetMode,
    onDismiss: () -> Unit,
    onChangeBottomSheetMode: (TaskBottomSheetMode) -> Unit,
    onSaveTask: (TaskUpdateDetails, isNewTask: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    taskBottomSheetState: SheetState
) {
    val saveAndDismiss: (TaskUpdateDetails, isNewTask: Boolean) -> Unit = { task, isNewTask ->
        onDismiss()
        onSaveTask(task, isNewTask)
    }

    ModalBottomSheet(
        modifier = modifier,
        sheetState = taskBottomSheetState,
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        dragHandle = null
    ) {
        when (taskBottomSheetMode) {
            TaskBottomSheetMode.CREATE -> {
                TasksBottomSheetCreateContent(
                    modifier = Modifier.padding(MaterialTheme.dimensions.small),
                    onSaveTask = onSaveTask
                )
            }

            TaskBottomSheetMode.VIEW -> {
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
                selectedTask?.let { task ->
                    TasksBottomSheetEditContent(
                        modifier = Modifier.padding(MaterialTheme.dimensions.small),
                        selectedTask = task,
                        onSaveTask = onSaveTask
                    )
                }
            }
        }
    }
}

@Composable
internal fun TaskBottomSheetEditableContent(
    contentTitle: String,
    title: TextFieldValue,
    description: TextFieldValue,
    onSaveTask: (Pair<String, String>) -> Unit,
    onTitleChanged: (TextFieldValue) -> Unit,
    onDescriptionChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = contentTitle,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Button(
                modifier = Modifier.wrapContentSize(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(CornerSize(MaterialTheme.dimensions.xxSmall)),
                contentPadding = PaddingValues(horizontal = MaterialTheme.dimensions.xSmall),
                onClick = {
                    if (title.text.isNotEmpty() || description.text.isNotEmpty()) {
                        onSaveTask(Pair(title.text, description.text))
                    }
                }
            ) {
                Text(
                    text = stringResource(Res.string.save),
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }

        DonezoTextField(
            value = title,
            modifier = Modifier.fillMaxWidth().padding(top = MaterialTheme.dimensions.small).focusRequester(focusRequester),
            placeholderText = stringResource(Res.string.title_placeholder),
            isSingleLine = true,
            onValueChanged = { newTextFieldValue ->
                onTitleChanged(newTextFieldValue)
            }
        )

        DonezoTextField(
            value = description,
            modifier = Modifier.fillMaxWidth().padding(top = MaterialTheme.dimensions.small).height(
                MaterialTheme.dimensions.custom112
            ),
            placeholderText = stringResource(Res.string.description_placeholder),
            onValueChanged = { newTextFieldValue ->
                onDescriptionChanged(newTextFieldValue)
            },
            maxLines = 5
        )

        LaunchedEffect(Unit) {
            /*
             * On iOS, the UI sometimes isn’t ready for focus immediately
             * after entering the composition so a small delay
             * improves reliability and avoids any UI jank.
             */
            delay(300)
            focusRequester.requestFocus()
        }
    }
}

package com.senijoshua.donezo.presentation.tasks.components.bottomsheet

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.senijoshua.donezo.presentation.components.DonezoTextField
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.save
import donezo.multiplatform.generated.resources.task_bottom_sheet_description_placeholder
import donezo.multiplatform.generated.resources.task_bottom_sheet_title
import donezo.multiplatform.generated.resources.task_bottom_sheet_title_placeholder
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TaskBottomSheetCreateContent(
    modifier: Modifier = Modifier,
    onSaveTask: () -> Unit,
) {
    var title by mutableStateOf(TextFieldValue(""))
    var description by mutableStateOf(TextFieldValue(""))

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.task_bottom_sheet_title),
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
                        onSaveTask()
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
            modifier = Modifier.fillMaxWidth().padding(top = MaterialTheme.dimensions.small),
            placeholderText = stringResource(Res.string.task_bottom_sheet_title_placeholder),
            isSingleLine = true,
            onValueChanged = { newTextFieldValue ->
                title = newTextFieldValue
            }
        )

        DonezoTextField(
            value = description,
            modifier = Modifier.fillMaxWidth().padding(top = MaterialTheme.dimensions.small).height(
                MaterialTheme.dimensions.custom112
            ),
            placeholderText = stringResource(Res.string.task_bottom_sheet_description_placeholder),
            onValueChanged = { newTextFieldValue ->
                description = newTextFieldValue
            },
            maxLines = 5
        )
    }
}

@Preview
@Composable
private fun TaskBottomSheetCreateContentLightPreview() {
    DonezoTheme {
        Surface {
            TaskBottomSheetCreateContent(
                onSaveTask = {},
            )
        }
    }
}

@Preview
@Composable
private fun TaskBottomSheetCreateContentDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
            TaskBottomSheetCreateContent(
                onSaveTask = {},
            )
        }
    }
}

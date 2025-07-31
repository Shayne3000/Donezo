package com.senijoshua.donezo.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.task_bottom_sheet_title_placeholder
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DonezoTextField(
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    maxLines: Int,
    modifier: Modifier = Modifier,
    isSingleLine: Boolean = false,
) {
    TextField(
        modifier = Modifier.padding(top = MaterialTheme.dimensions.xSmall),
        value = value,
        onValueChange = { newTextFieldValue ->
            onValueChanged(newTextFieldValue)
        },
        singleLine = isSingleLine,
        placeholder = {
            Text(
                text = stringResource(Res.string.task_bottom_sheet_title_placeholder),
                style = MaterialTheme.typography.bodySmall,
            )
        },
        maxLines = maxLines,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.tertiary,
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
            focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    )
}

@Preview
@Composable
private fun DonezoTextFieldLightPreview() {
    DonezoTheme {
        Surface {
        }
    }
}

@Preview
@Composable
private fun DonezoTextFieldDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
        }
    }
}


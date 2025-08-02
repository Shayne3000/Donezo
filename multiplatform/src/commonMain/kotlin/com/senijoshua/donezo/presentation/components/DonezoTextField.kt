package com.senijoshua.donezo.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DonezoTextField(
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    maxLines: Int = Int.MAX_VALUE,
    placeholderText: String,
    modifier: Modifier = Modifier,
    isSingleLine: Boolean = false,
) {
    TextField(
        modifier = modifier.clip(
            RoundedCornerShape(
                MaterialTheme.dimensions.xxSmall
            )
        ),
        value = value,
        onValueChange = { newTextFieldValue ->
            onValueChanged(newTextFieldValue)
        },
        singleLine = isSingleLine,
        placeholder = {
            Text(
                modifier = Modifier.padding(top = MaterialTheme.dimensions.xxSmall),
                text = placeholderText,
                style = MaterialTheme.typography.bodySmall,
            )
        },
        maxLines = maxLines,
        textStyle = MaterialTheme.typography.bodySmall,
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
            DonezoTextField(
                value = TextFieldValue(""),
                modifier = Modifier.padding(MaterialTheme.dimensions.xSmall),
                placeholderText = "Enter text",
                onValueChanged = {
                }
            )
        }
    }
}

@Preview
@Composable
private fun DonezoTextFieldDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
            DonezoTextField(
                value = TextFieldValue(""),
                modifier = Modifier.padding(MaterialTheme.dimensions.xSmall),
                placeholderText = "Enter text",
                onValueChanged = {}
            )
        }
    }
}


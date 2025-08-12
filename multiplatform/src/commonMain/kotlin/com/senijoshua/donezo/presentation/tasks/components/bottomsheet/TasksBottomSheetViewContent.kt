package com.senijoshua.donezo.presentation.tasks.components.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.senijoshua.donezo.presentation.tasks.model.TodoTask
import com.senijoshua.donezo.presentation.tasks.model.previewTasks
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.ic_edit
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TaskBottomSheetViewContent(
    selectedTask: TodoTask,
    onEditClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        IconButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onEditClicked
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_edit),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = selectedTask.title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.dimensions.xxSmall)
                .verticalScroll(rememberScrollState()),
            text = selectedTask.description,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 5
        )
    }
}

@Preview
@Composable
private fun TaskBottomSheetViewContentLightPreview() {
    DonezoTheme {
        Surface {
            TaskBottomSheetViewContent(
                selectedTask = previewTasks[0],
                onEditClicked = {},
            )
        }
    }
}

@Preview
@Composable
private fun TaskBottomSheetViewContentDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
            TaskBottomSheetViewContent(
                selectedTask = previewTasks[0],
                onEditClicked = {},
            )
        }
    }
}

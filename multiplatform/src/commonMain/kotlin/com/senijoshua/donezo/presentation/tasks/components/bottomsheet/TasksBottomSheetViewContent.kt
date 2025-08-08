package com.senijoshua.donezo.presentation.tasks.components.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.senijoshua.donezo.presentation.tasks.TodoTasks
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.ic_edit
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TaskBottomSheetViewContent(
    modifier: Modifier = Modifier,
    selectedTask: TodoTasks,
    onEditClicked: () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        IconButton(
            modifier = Modifier.offset(x = MaterialTheme.dimensions.xSmall),
            onClick = onEditClicked
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_edit),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth().padding(top = MaterialTheme.dimensions.xxSmall),
            text = selectedTask.title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            modifier = Modifier.fillMaxWidth().padding(top = MaterialTheme.dimensions.xxSmall),
            text = selectedTask.description,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall
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

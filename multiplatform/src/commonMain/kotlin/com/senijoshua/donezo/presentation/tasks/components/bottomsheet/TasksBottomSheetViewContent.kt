package com.senijoshua.donezo.presentation.tasks.components.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextOverflow
import com.senijoshua.donezo.presentation.tasks.model.PresentationTask
import com.senijoshua.donezo.presentation.tasks.model.previewTasks
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import com.senijoshua.donezo.presentation.utils.getDateFormat
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.ic_date
import donezo.multiplatform.generated.resources.ic_edit
import kotlinx.datetime.format
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TaskBottomSheetViewContent(
    selectedTask: PresentationTask,
    onEditClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().height(MaterialTheme.dimensions.xLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(start = MaterialTheme.dimensions.small),
                painter = painterResource(Res.drawable.ic_date),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(start = MaterialTheme.dimensions.xxSmall),
                text = selectedTask.createdTime.format(getDateFormat()),
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = onEditClicked
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_edit),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = MaterialTheme.dimensions.small,
                    end = MaterialTheme.dimensions.small,
                ),
            text = selectedTask.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.dimensions.xxSmall,
                    start = MaterialTheme.dimensions.small,
                    end = MaterialTheme.dimensions.small,
                    bottom = MaterialTheme.dimensions.small
                )
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

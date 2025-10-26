package com.senijoshua.donezo.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.senijoshua.donezo.presentation.model.Task
import com.senijoshua.donezo.presentation.model.tasksPreview
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import com.senijoshua.donezo.presentation.utils.getDateFormat
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.complete_task_text
import donezo.multiplatform.generated.resources.ic_completed
import donezo.multiplatform.generated.resources.ic_date
import donezo.multiplatform.generated.resources.ic_delete
import donezo.multiplatform.generated.resources.ic_edit
import donezo.multiplatform.generated.resources.ic_tasks
import donezo.multiplatform.generated.resources.todo_task_text
import kotlinx.datetime.format
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Reusable task item component
 */
@Composable
fun TaskItem(
    task: Task,
    isCompleted: Boolean,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    onEdit: (Task) -> Unit = {},
    onMarkedAsDone: () -> Unit = {},
    onMarkedAsTodo: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    val titleTextStyle = MaterialTheme.typography.titleMedium.copy(
        textDecoration = if (isCompleted) TextDecoration.LineThrough else null
    )
    val descriptionTextStyle = MaterialTheme.typography.bodySmall.copy(
        textDecoration = if (isCompleted) TextDecoration.LineThrough else null
    )
    val opacity = if (isCompleted) 0.5f else 1f
    val buttonIcon = if (isCompleted) {
        painterResource(Res.drawable.ic_tasks)
    } else {
        painterResource(Res.drawable.ic_completed)
    }
    val buttonText = if (isCompleted) {
        stringResource(Res.string.todo_task_text)
    } else {
        stringResource(Res.string.complete_task_text)
    }
    val onButtonClick = {
        if (isCompleted) onMarkedAsTodo() else onMarkedAsDone()
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.dimensions.custom172),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.dimensions.xxSmall)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(MaterialTheme.dimensions.xSmall))
                .clickable { onClick() }
                .padding(horizontal = MaterialTheme.dimensions.small)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(MaterialTheme.dimensions.xLarge)
                    .alpha(opacity),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_date),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(start = MaterialTheme.dimensions.xxSmall),
                    text = task.createdTime.format(getDateFormat()),
                    style = MaterialTheme.typography.bodySmall,
                )
                if (!isCompleted) {
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        modifier = Modifier.offset(x = MaterialTheme.dimensions.xSmall),
                        onClick = {
                            onEdit(task)
                        }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_edit),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.padding(top = MaterialTheme.dimensions.xxSmall).alpha(opacity),
                text = task.title,
                style = titleTextStyle,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Box(
                Modifier
                    .padding(vertical = MaterialTheme.dimensions.xSmall)
                    .fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .alpha(opacity),
                    text = task.description,
                    style = descriptionTextStyle,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = MaterialTheme.dimensions.small,
                        )
                        .align(alignment = Alignment.BottomStart),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        modifier = Modifier.offset(x = -(MaterialTheme.dimensions.xSmall)),
                        onClick = {
                            onDelete()
                        }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_delete),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                    Button(
                        modifier = Modifier.wrapContentSize(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(CornerSize(MaterialTheme.dimensions.xxSmall)),
                        contentPadding = PaddingValues(horizontal = MaterialTheme.dimensions.small),
                        onClick = onButtonClick
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = MaterialTheme.dimensions.xxSmall),
                            painter = buttonIcon,
                            contentDescription = null
                        )
                        Text(
                            text = buttonText,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskItemLightPreview() {
    DonezoTheme {
        Surface {
            TaskItem(
                modifier = Modifier.padding(MaterialTheme.dimensions.small),
                task = tasksPreview[0],
                isCompleted = false,
                onDelete = {},
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun TaskItemDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
            TaskItem(
                modifier = Modifier.padding(16.dp),
                task = tasksPreview[0],
                isCompleted = false,
                onDelete = {},
                onClick = {}
            )
        }
    }
}

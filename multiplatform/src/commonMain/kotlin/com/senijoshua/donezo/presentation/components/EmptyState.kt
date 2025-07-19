package com.senijoshua.donezo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.empty_state_text
import donezo.multiplatform.generated.resources.ic_info
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Component representing the empty state of the bottom tabs
 */
@Composable
fun EmptyState(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimensions.medium)
                .clip(RoundedCornerShape(MaterialTheme.dimensions.medium))
                .background(color = MaterialTheme.colorScheme.tertiaryContainer),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.padding(start = MaterialTheme.dimensions.small),
                painter = painterResource(Res.drawable.ic_info),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Text(
                modifier = Modifier.padding(MaterialTheme.dimensions.small),
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun EmptyStateLightPreview() {
    DonezoTheme {
        Surface {
            EmptyState(text = stringResource(Res.string.empty_state_text))
        }
    }
}

@Preview
@Composable
fun EmptyStateDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
            EmptyState(text = stringResource(Res.string.empty_state_text))
        }
    }
}

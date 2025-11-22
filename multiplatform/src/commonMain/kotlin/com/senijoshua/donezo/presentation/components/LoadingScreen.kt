package com.senijoshua.donezo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.presentation.theme.dimensions
import com.valentinilk.shimmer.shimmer
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Loading screen with a shimmer
 */
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    shouldShowEditButton: Boolean = true
) {
    Column(modifier) {
        repeat(2) {
            Card(
                modifier = Modifier
                    .shimmer()
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = MaterialTheme.dimensions.xSmall),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.dimensions.small)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(MaterialTheme.dimensions.xLarge),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(
                                    width = MaterialTheme.dimensions.custom112,
                                    height = MaterialTheme.dimensions.medium
                                )
                                .clip(RoundedCornerShape(MaterialTheme.dimensions.xxSmall))
                                .background(color = MaterialTheme.colorScheme.outlineVariant)
                        )
                        if (shouldShowEditButton) {
                            Spacer(modifier = Modifier.weight(1f))
                            Box(
                                modifier = Modifier
                                    .size(MaterialTheme.dimensions.medium)
                                    .clip(RoundedCornerShape(MaterialTheme.dimensions.xxSmall))
                                    .background(color = MaterialTheme.colorScheme.outlineVariant)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimensions.xxSmall)
                            .fillMaxWidth()
                            .height(MaterialTheme.dimensions.medium)
                            .clip(RoundedCornerShape(MaterialTheme.dimensions.xxSmall))
                            .background(color = MaterialTheme.colorScheme.outlineVariant)
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimensions.xSmall)
                            .fillMaxWidth()
                            .height(MaterialTheme.dimensions.xLarge)
                            .clip(RoundedCornerShape(MaterialTheme.dimensions.xxSmall))
                            .background(color = MaterialTheme.colorScheme.outlineVariant)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = MaterialTheme.dimensions.small,
                                bottom = MaterialTheme.dimensions.xSmall
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .size(MaterialTheme.dimensions.medium)
                                .clip(RoundedCornerShape(MaterialTheme.dimensions.xxSmall))
                                .background(color = MaterialTheme.colorScheme.outlineVariant)
                        )
                        Box(
                            modifier = Modifier
                                .size(
                                    width = MaterialTheme.dimensions.custom112,
                                    height = MaterialTheme.dimensions.medium
                                )
                                .clip(RoundedCornerShape(MaterialTheme.dimensions.xxSmall))
                                .background(color = MaterialTheme.colorScheme.outlineVariant)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoadingScreenLightPreview() {
    DonezoTheme {
        Surface {
            LoadingScreen(modifier = Modifier.padding(horizontal = MaterialTheme.dimensions.small))
        }
    }
}

@Preview
@Composable
private fun LoadingScreenDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {
            LoadingScreen(modifier = Modifier.padding(MaterialTheme.dimensions.small))
        }
    }
}

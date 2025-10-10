package com.senijoshua.donezo.presentation.features.characters.detail

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CharactersDetailScreen(
    onBackPressed: () -> Unit
) {
    CharactersDetailContent(
        onBackPressed = onBackPressed
    )
}

@Composable
private fun CharactersDetailContent(
    onBackPressed: () -> Unit
) {

}

@Preview
@Composable
private fun CharactersDetailLightPreview() {
    DonezoTheme {
        Surface {

        }
    }
}

@Preview
@Composable
private fun CharactersDetailDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {

        }
    }
}


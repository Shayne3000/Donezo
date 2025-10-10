package com.senijoshua.donezo.presentation.features.characters.list

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.senijoshua.donezo.presentation.features.characters.CharactersViewModel
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharactersListScreen(
    navigateToDetail: (Int) -> Unit,
    viewModel: CharactersViewModel = koinViewModel()
) {
    CharactersListContent(
        onCharacterItemClicked = { id -> navigateToDetail(id) }
    )
}

@Composable
private fun CharactersListContent(
    onCharacterItemClicked: (Int) -> Unit = {}
) {

}

@Preview
@Composable
private fun CharactersListLightPreview() {
    DonezoTheme {
        Surface {

        }
    }
}

@Preview
@Composable
private fun CharactersListDarkPreview() {
    DonezoTheme(darkTheme = true) {
        Surface {

        }
    }
}

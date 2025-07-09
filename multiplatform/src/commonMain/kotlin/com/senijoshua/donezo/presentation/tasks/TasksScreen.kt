package com.senijoshua.donezo.presentation.tasks

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TasksScreen(
    viewModel: TasksViewModel = koinViewModel(),
) {

    TasksContent()
}

@Composable
private fun TasksContent() {

}

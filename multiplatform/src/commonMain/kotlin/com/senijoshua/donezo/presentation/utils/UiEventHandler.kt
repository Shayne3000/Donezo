package com.senijoshua.donezo.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

/**
 * Lifecycle-aware handler of one-time UI events
 * reusable across screens.
 */
@Composable
fun <T> UiEventHandler(
    eventFlow: SharedFlow<T>,
    doOnUiEvent: suspend (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            eventFlow.collectLatest { event ->
                doOnUiEvent(event)
            }
        }
    }
}


package com.senijoshua.donezo.presentation.utils

import androidx.compose.runtime.Composable
import donezo.multiplatform.generated.resources.Res
import donezo.multiplatform.generated.resources.generic_error_message
import org.jetbrains.compose.resources.stringResource

@Composable
fun getGenericErrorMessage(): String {
    return stringResource(Res.string.generic_error_message)
}

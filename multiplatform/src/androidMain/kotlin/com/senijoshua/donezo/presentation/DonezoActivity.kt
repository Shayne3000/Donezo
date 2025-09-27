package com.senijoshua.donezo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.senijoshua.donezo.App
import com.senijoshua.donezo.presentation.features.root.Root
import com.senijoshua.donezo.presentation.theme.DonezoTheme

class DonezoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@PreviewLightDark
@Composable
fun DonezoAppPreview() {
    DonezoTheme {
        Root()
    }
}

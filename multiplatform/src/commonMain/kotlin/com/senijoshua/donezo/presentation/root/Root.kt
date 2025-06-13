package com.senijoshua.donezo.presentation.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * App-level composable for holding app-level UI components and logic
 */
@Composable
fun Root(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
//        NavHost(navController = navController) {
//
//        }
    }
}



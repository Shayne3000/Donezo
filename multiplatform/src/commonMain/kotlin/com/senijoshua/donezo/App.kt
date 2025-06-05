package com.senijoshua.donezo

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.senijoshua.donezo.di.appModule
import com.senijoshua.donezo.di.platformModule
import com.senijoshua.donezo.utils.isDebugBuild
import okio.FileSystem
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule, platformModule)
    }) {
        // Coil ImageLoader configuration
        setSingletonImageLoaderFactory { context: PlatformContext ->
            ImageLoader.Builder(context)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .memoryCache {
                    MemoryCache.Builder()
                        .maxSizePercent(context, percent = 0.25)
                        .build()
                }
                .diskCachePolicy(CachePolicy.ENABLED)
                .diskCache {
                    DiskCache.Builder()
                        .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
                        .maxSizePercent(percent = 0.02)
                        .build()
                }
                .crossfade(true)
                .apply {
                    if (isDebugBuild()) {
                        logger(DebugLogger())
                    }
                }
                .build()
        }

        // TODO Setup AppTheme here and call Root Composable
    }
}

@Composable
fun Root() {
    // TODO Setup Navhost within this root composable
}

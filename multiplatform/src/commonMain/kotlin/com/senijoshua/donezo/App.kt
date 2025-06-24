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
import com.senijoshua.donezo.presentation.root.Root
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.utils.isDebugBuild
import okio.FileSystem

@Composable
fun App() {
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

    DonezoTheme {
        Root()
    }
}

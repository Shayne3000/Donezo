package com.senijoshua.donezo

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.senijoshua.donezo.presentation.features.root.Root
import com.senijoshua.donezo.presentation.theme.DonezoTheme
import com.senijoshua.donezo.utils.isDebugBuild
import io.ktor.client.HttpClient
import okio.FileSystem
import org.koin.compose.koinInject

@Composable
fun App() {
    val httpClient: HttpClient = koinInject<HttpClient>()

    // Coil Global ImageLoader configuration
    setSingletonImageLoaderFactory { context: PlatformContext ->
        ImageLoader.Builder(context)
            .components {
                /*
                 * Inject the app's Ktor instance as the http client
                 * coil would use for retrieving images from the backend
                 * since the instance Coil uses by default sometimes adds
                 * headers that services employing Cloudflare for security
                 * do not accept.
                 */
                add(KtorNetworkFetcherFactory(httpClient))
            }
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

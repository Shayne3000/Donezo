package com.senijoshua.donezo.utils

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual fun isDebugBuild() = Platform.isDebugBinary

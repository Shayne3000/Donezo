package com.senijoshua.donezo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
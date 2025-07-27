import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin { // Config block for the Compose multiplatform project
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        // configuration for the resulting framework binary that will be generated from the
        // KMP shared module for when one wants to integrate/import said shared module into an iOS project.
        it.binaries.framework {
            baseName = "Multiplatform" // name of the resulting framework binary to be imported into an iOS project
            isStatic = true // builds framework as static lib instead of a dynamic one.
            // Static frameworks are often preferred for easier integration and fewer runtime issues on iOS.
        }
    }

    sourceSets {
        all{
            languageSettings {
                compilerOptions{
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }
        // Android platform-specific source-set
        androidMain.dependencies {
            // Android platform-specific dependencies

            // Activity Compose i.e. Component Activity
            implementation(libs.androidx.activity.compose)

            // Ktor OkHttp engine
            implementation(libs.ktor.client.okhttp)

            // Compose Previews
            implementation(compose.uiTooling)

            // Koin Android
            implementation(libs.koin.android)

            // Android Coroutines
            implementation(libs.kotlinx.coroutines.android)
        }

        // Common code source-set
        commonMain.dependencies {
            //put your shared/multiplatform dependencies here

            // Common code Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Coil
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            // Compose UI
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)

            // Scalable pixels
            implementation(libs.multiplatform.scalable.pixels)

            // Shimmer
            implementation(libs.shimmer)

            // Date time
            implementation(libs.kotlinx.datetime)

            // Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.content.negotiaton)

            // Kotlin Serialization
            implementation(libs.kotlinx.serialization.json)

            // Ktor kotlinx serialization registration artifact
            implementation(libs.ktor.serialization.kotlinx.json)

            // Lifecycle compose
            implementation(libs.lifecycle.compose)

            // Navigation compose
            implementation(libs.navigation.compose)

            // Room
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
        }
        // Common code test counterpart source-set
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        // iOS platform-specific source-set
        iosMain.dependencies {
            // iOS platform-specific dependencies

            // Ktor Darwin Engine
            implementation(libs.ktor.client.darwin)
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}

android { // build configuration block for the android target of the multiplatform module
    namespace = "com.senijoshua.donezo"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

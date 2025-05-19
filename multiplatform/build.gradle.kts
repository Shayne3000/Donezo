import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
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
        // KMP shared module when one wants to integrate said shared module into an iOS project.
        it.binaries.framework {
            baseName = "multiplatform" // name of the resulting framework binary for importing in an iOS project
            isStatic = true // builds framework as static lib instead of a dynmanic one.
            // Static frameworks are often preferred for easier integration and fewer runtime issues on iOS.
        }
    }

    sourceSets {
        // Android platform-specific source-set
        androidMain.dependencies {
            // Android platform-specific dependencies

            // Activity Compose i.e. Component Activity
            implementation(libs.androidx.activity.compose)

            // Compose UI
            implementation(compose.preview)

            // Ktor OkHttp engine
            implementation(libs.ktor.client.okhttp)
            // Android Coroutines
            implementation(libs.kotlinx.coroutines.android)
        }

        // Common code source-set
        commonMain.dependencies {
            //put your shared/multiplatform dependencies here

            // Compose UI
            implementation(compose.ui)

            // Room
            implementation(libs.room.runtime)

            // Ktor
            implementation(libs.ktor.client.core)

            // Kotlin Serialization
            implementation(libs.kotlinx.serialization.json)

            // Common code Coroutines
            implementation(libs.kotlinx.coroutines.core)
        }
        // Common code test counterpart source-set
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        // iOS platform-specific source-set
        iosMain.dependencies {
            // iOS platform-specific dependencies
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

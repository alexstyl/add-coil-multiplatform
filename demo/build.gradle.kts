plugins {
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.application)
}

java {
    toolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }
}


val demoPackageName = "com.alexstyl.addcoilmultiplatform.demo"

kotlin {
    jvmToolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }
    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }

    jvm("desktop")

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(project(":addcoilmultiplatform"))
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("androidx.compose.ui:ui:1.6.6")
                implementation("androidx.activity:activity-compose:1.9.0")
            }
        }
    }
}

compose.experimental {
    web.application {}
}

compose.desktop {
    application {
        mainClass = "${demoPackageName}.MainKt"
    }
}

android {
    namespace = demoPackageName
    compileSdk = 34
    defaultConfig {
        minSdk = 21
        targetSdk = 34

        applicationId = demoPackageName
        versionCode = 1
        versionName = "1.0.0"
    }
}
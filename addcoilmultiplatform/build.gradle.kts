@file:Suppress("UnstableApiUsage")

import org.jetbrains.compose.internal.utils.getLocalProperty

plugins {
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.multiplatform)
    id("com.android.library")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
}

val publishGroupId = "com.alexstyl"
val publishArtifactId = "addcoilmultiplatform"
val publishVersion = "3.0.0-rc01"
val githubUrl = "github.com/alexstyl/addcoilmultiplatform"

java {
    toolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    jvmToolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }

    androidTarget {
        publishLibraryVariants("release", "debug")
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    jvm()

    wasmJs {
        browser()
    }

    js(IR) {
        browser()
    }

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "AddCoilMultiplatform"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.ktor.client.core)
                implementation(libs.coil)
                api(libs.coil.compose)
                api(libs.coil.compose.core)

                implementation(libs.coil.network.ktor)
            }
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)

            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
        }

        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

android {
    namespace = "com.alexstyl.addcoilmultiplatform"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
        targetSdk = 34
    }
}

val dokkaOutputDir = buildDir.resolve("dokka")
tasks.dokkaHtml { outputDirectory.set(file(dokkaOutputDir)) }
val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") { delete(dokkaOutputDir) }
val javadocJar = tasks.create<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    from(dokkaOutputDir)
}

group = publishGroupId
version = publishVersion


afterEvaluate {
    publishing {
        publications {
            withType<MavenPublication> {
                artifact(javadocJar)

                pom {
                    name.set("Add Coil Multiplatform")
                    description.set("Add Coil to your Compose Multiplatform project with a single dependency")
                    url.set("https://${githubUrl}")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://${githubUrl}/blob/main/LICENSE")
                        }
                    }
                    issueManagement {
                        system.set("GitHub Issues")
                        url.set("https://${githubUrl}/issues")
                    }
                    developers {
                        developer {
                            id.set("alexstyl")
                            name.set("Alex Styl")
                            email.set("alex@alexstyl.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:${githubUrl}.git")
                        developerConnection.set("scm:git:ssh://${githubUrl}.git")
                        url.set("https://${githubUrl}/tree/main")
                    }
                }
            }
        }
        // TODO: remove after https://youtrack.jetbrains.com/issue/KT-46466 is fixed
        project.tasks.withType(AbstractPublishToMaven::class.java).configureEach {
            dependsOn(project.tasks.withType(Sign::class.java))
        }
    }
}

signing {
    useInMemoryPgpKeys(
        getLocalProperty("signing.keyId") ?: System.getenv("SIGNING_KEY_ID"),
        getLocalProperty("signing.key") ?: System.getenv("SIGNING_KEY"),
        getLocalProperty("signing.password") ?: System.getenv("SIGNING_PASSWORD"),
    )
    sign(publishing.publications)
}

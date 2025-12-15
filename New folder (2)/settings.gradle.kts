pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.13.1" apply false
        id("com.android.library") version "8.13.1" apply false
        id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://storage.googleapis.com/download.flutter.io") }

    }
}

rootProject.name = "salon"
include(":app")
val flutterProjectRoot = file("../contact_flutter_module")
apply(from = File(flutterProjectRoot, ".android/include_flutter.groovy"))

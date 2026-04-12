rootProject.name = "morphe-patches-template"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/MorpheApp/registry")
            credentials {
                val localProps = java.util.Properties()
                val localPropsFile = java.io.File(settingsDir, "local.properties")
                if (localPropsFile.exists()) {
                    localPropsFile.inputStream().use { localProps.load(it) }
                }

                username = localProps.getProperty("gpr.user")?.takeUnless { it.isBlank() }
                    ?: providers.gradleProperty("gpr.user").orNull?.takeUnless { it.isBlank() }
                    ?: System.getenv("GITHUB_ACTOR")?.takeUnless { it.isBlank() }
                password = localProps.getProperty("gpr.key")?.takeUnless { it.isBlank() }
                    ?: providers.gradleProperty("gpr.key").orNull?.takeUnless { it.isBlank() }
                    ?: System.getenv("GITHUB_TOKEN")?.takeUnless { it.isBlank() }
            }
        }
        maven { url = uri("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/MorpheApp/registry")
            credentials {
                val localProps = java.util.Properties()
                val localPropsFile = java.io.File(settingsDir, "local.properties")
                if (localPropsFile.exists()) {
                    localPropsFile.inputStream().use { localProps.load(it) }
                }

                username = localProps.getProperty("gpr.user")?.takeUnless { it.isBlank() }
                    ?: providers.gradleProperty("gpr.user").orNull?.takeUnless { it.isBlank() }
                    ?: System.getenv("GITHUB_ACTOR")?.takeUnless { it.isBlank() }
                password = localProps.getProperty("gpr.key")?.takeUnless { it.isBlank() }
                    ?: providers.gradleProperty("gpr.key").orNull?.takeUnless { it.isBlank() }
                    ?: System.getenv("GITHUB_TOKEN")?.takeUnless { it.isBlank() }
            }
        }
        maven { url = uri("https://jitpack.io") }
    }
}

plugins {
    id("app.morphe.patches") version "1.3.0"
}

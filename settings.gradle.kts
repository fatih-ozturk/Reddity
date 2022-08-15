pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}

include(":app")
include(":auth")
include(":base")
include(":compose-ui")
include(":data")
include(":domain")
include(":ui-home")
enableFeaturePreview("VERSION_CATALOGS")

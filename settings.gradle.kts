pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
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
rootProject.name = "Reddity"

include(":app")
include(":core:auth")
include(":core:base")
include(":core:data")
include(":core:database")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:testing")
include(":core:ui")
include(":feature:chat")
include(":feature:createpost")
include(":feature:explore")
include(":feature:home")
include(":feature:notification")
include(":feature:login")
enableFeaturePreview("VERSION_CATALOGS")

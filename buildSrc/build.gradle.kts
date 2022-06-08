repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    `java-gradle-plugin`
}

dependencies {
    api(libs.android.pluginGradle)
    api(libs.kotlin.pluginGradle)
    api(libs.kotlin.extensions)
    api(libs.hilt.pluginGradle)
    api(libs.gradleDependencyUpdate)
    api(libs.ktlint)
    api(libs.spotless)
}

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
    implementation(libs.android.pluginGradle)
    implementation(libs.kotlin.pluginGradle)
    implementation(libs.kotlin.extensions)
    implementation(libs.hilt.pluginGradle)
    implementation(libs.gradleDependencyUpdate)
    implementation(libs.ktlint)
    implementation(libs.spotless)
}

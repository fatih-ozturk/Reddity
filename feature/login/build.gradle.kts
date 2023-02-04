plugins {
    id("reddity.android.feature")
    id("reddity.android.library.compose")
}

android {
    namespace = "com.reddity.app.login"
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:auth"))
    implementation(project(":core:model"))

    implementation(libs.accompanist.navigation)
}

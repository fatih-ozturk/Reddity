plugins {
    id("reddity.android.library")
    id("reddity.android.hilt")
}

android {
    namespace = "com.reddity.app.sync"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:auth"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    implementation(libs.kotlin.coroutines.android)

    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.work.ktx)
    implementation(libs.androidx.hilt.work)
}

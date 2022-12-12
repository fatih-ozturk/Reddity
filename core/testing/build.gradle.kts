/*
 * Copyright 2022 Fatih OZTURK
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "com.reddity.app.testing"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    androidTestImplementation(kotlin("test"))
    testImplementation(kotlin("test"))
    api(platform(libs.compose.bom))
    api(libs.junit)
    api(libs.androidx.test.core)
    api(libs.kotlin.coroutines.test)
    api(libs.turbine)
    api(libs.mockK)
    api(libs.truth)

    api(libs.androidx.test.runner)
    api(libs.androidx.test.rules)
    api(libs.compose.ui.test)
    api(libs.hilt.testing)
    api(libs.okhttp.mockwebserver)

    debugApi(libs.compose.ui.testManifest)
    kapt(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)
}

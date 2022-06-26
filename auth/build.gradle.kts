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
    compileSdk = Config.SdkVersions.compile

    defaultConfig {
        minSdk = Config.SdkVersions.min
        addManifestPlaceholders(mapOf("appAuthRedirectScheme" to "empty"))
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":base"))
    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    implementation(libs.timber)

    implementation(libs.androidx.browser)
    implementation(libs.androidx.core)

    implementation(libs.czerwinski.android.hilt.extension)
    kapt(libs.czerwinski.android.hilt.processor)

    api(libs.appauth)
}

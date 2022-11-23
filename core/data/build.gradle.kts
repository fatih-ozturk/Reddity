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
    id("kotlin-parcelize")
}

android {
    namespace = "com.reddity.app.data"
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
    implementation(project(":core:base"))
    implementation(project(":core:network"))

    implementation(libs.timber)

    implementation(libs.hilt.library)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.hilt.work)
    kapt(libs.androidx.hilt.compiler)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.retrofit.retrofit)
    implementation(libs.retrofit.gsonConverter)
    implementation(libs.retrofit.mock)
    implementation(libs.retrofit.moshiConverter)
    implementation(libs.moshi.core)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapter)

    implementation(libs.okhttp.okhttp)
    implementation(libs.okhttp.loggingInterceptor)

    testImplementation(libs.junit)
    testImplementation(libs.mockK)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.truth)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.hilt.testing)
    testImplementation(libs.kotlin.coroutines.android)
}

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
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("reddity.android.library")
    id("reddity.android.hilt")
    id("kotlin-parcelize")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.reddity.app.network"

    defaultConfig {
        manifestPlaceholders += mapOf(
            "appAuthRedirectScheme" to "empty"
        )
    }
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:auth"))
    implementation(project(":core:model"))

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.appauth)

    implementation(libs.retrofit.retrofit)
    implementation(libs.retrofit.gsonConverter)
    implementation(libs.retrofit.mock)
    implementation(libs.retrofit.moshiConverter)
    implementation(libs.moshi.core)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapter)
    ksp(libs.moshi.codegen)
    implementation(libs.okhttp.okhttp)
    implementation(libs.okhttp.loggingInterceptor)
}

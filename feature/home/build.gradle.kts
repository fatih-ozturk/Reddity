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
    id("reddity.android.feature")
    id("reddity.android.library.compose")
}

android {
    namespace = "com.reddity.app.home"
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:auth"))
    implementation(project(":core:model"))

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    implementation(libs.coil.kt.gif)

    implementation(libs.exoplayer)

    implementation(libs.accompanist.pager.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.swiperefresh)
}

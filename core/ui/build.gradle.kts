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
    id("reddity.android.library")
    id("reddity.android.library.compose")
    id("reddity.android.hilt")
}

android {
    namespace = "com.reddity.app.ui"
}

dependencies {
    api(libs.compose.foundation.foundation)
    api(libs.compose.foundation.layout)
    api(libs.compose.material.iconsext)
    api(libs.compose.animation.animation)
    api(libs.compose.ui.tooling)
    api(libs.androidx.metrics)
    api(libs.compose.runtime)
    api(libs.compose.runtime.livedata)

    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.windowSizeClass)

    implementation(libs.lottie.compose)
    implementation(libs.accompanist.pager.pager)

    api(libs.accompanist.navigation.animation)
    api(libs.accompanist.systemuicontroller)
}

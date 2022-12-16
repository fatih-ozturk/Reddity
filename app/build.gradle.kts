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
    id("reddity.android.application")
    id("reddity.android.application.compose")
    id("reddity.android.hilt")
}

android {
    namespace = "com.reddity.app"

    defaultConfig {
        applicationId = "com.reddity.app"
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "com.reddity.app.testing.ReddityTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isJniDebuggable = true
            versionNameSuffix = "-dev"
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            proguardFiles.add(getDefaultProguardFile("proguard-android-optimize.txt"))
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(project(":core:base"))
    implementation(project(":core:data"))
    implementation(project(":core:ui"))
    implementation(project(":core:auth"))
    implementation(project(":core:database"))
    implementation(project(":core:domain"))

    implementation(project(":feature:home"))
    implementation(project(":feature:explore"))
    implementation(project(":feature:createpost"))
    implementation(project(":feature:chat"))
    implementation(project(":feature:notification"))

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.work.ktx)

    implementation(libs.accompanist.navigation)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.android)
}

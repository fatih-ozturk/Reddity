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
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = Config.applicationId
    compileSdk = Config.SdkVersions.compile
    buildToolsVersion = Config.buildToolsVersion

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.SdkVersions.min
        targetSdk = Config.SdkVersions.target
        versionCode = Config.Versions.versionCode
        versionName = Config.Versions.versionName
        testInstrumentationRunner = Config.testInstrumentationRunner
        addManifestPlaceholders(mapOf("appAuthRedirectScheme" to "empty"))
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
            buildConfigField("String", "REDDIT_CLIENT_ID", "\"\"")
            buildConfigField("String", "REDDIT_CLIENT_SECRET", "\"\"")
        }
        release {
            proguardFiles.add(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        resources.excludes.add("META-INF/*.kotlin_module")
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-rc02"
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(project(":auth"))
    implementation(project(":base"))
    implementation(project(":data"))
    implementation(project(":compose-ui"))
    implementation(project(":ui-home"))

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.emoji)
    api(libs.androidx.work.runtime)

    implementation(libs.compose.foundation.foundation)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material.material)
    implementation(libs.compose.material.iconsext)
    implementation(libs.compose.animation.animation)
    implementation(libs.compose.ui.tooling)

    implementation(libs.accompanist.navigation.animation)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.timber)

    implementation(libs.kotlin.stdlib)

    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.retrofit.retrofit)
    implementation(libs.retrofit.gsonConverter)
    implementation(libs.retrofit.mock)
    implementation(libs.retrofit.moshiConverter)
    implementation(libs.hilt.library)
    implementation(libs.androidx.hilt.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.hilt.work)
    kapt(libs.androidx.hilt.compiler)

    debugImplementation(libs.chucker.library)
    debugImplementation(libs.leakCanary)

    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.rules)
}

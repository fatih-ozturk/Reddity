/*
 * Copyright 2023 Fatih OZTURK
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
package com.reddity.app.base

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

class ContextUtils

fun Context.restartApp() {
    val packageManager: PackageManager = this.packageManager
    val intent: Intent = packageManager.getLaunchIntentForPackage(this.packageName)!!
    val componentName: ComponentName = intent.component!!
    val restartIntent: Intent = Intent.makeRestartActivityTask(componentName)
    this.startActivity(restartIntent)
    Runtime.getRuntime().exit(0)
}

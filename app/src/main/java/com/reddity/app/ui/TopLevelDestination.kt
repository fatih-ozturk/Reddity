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
package com.reddity.app.ui

import androidx.annotation.DrawableRes
import com.reddity.app.R

enum class TopLevelDestination(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
) {
    HOME(
        selectedIcon = R.drawable.icon_home_fill,
        unselectedIcon = R.drawable.icon_home,
    ),
    EXPLORE(
        selectedIcon = R.drawable.icon_discover_fill,
        unselectedIcon = R.drawable.icon_discover,
    ),
    CREATE_POST(
        selectedIcon = R.drawable.icon_add,
        unselectedIcon = R.drawable.icon_add,
    ),
    CHAT(
        selectedIcon = R.drawable.icon_chat_fill,
        unselectedIcon = R.drawable.icon_chat,
    ),
    NOTIFICATION(
        selectedIcon = R.drawable.icon_notification_fill,
        unselectedIcon = R.drawable.icon_notification,
    )
}

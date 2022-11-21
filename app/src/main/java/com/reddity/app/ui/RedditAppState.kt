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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.reddity.app.home.navigation.navigateToHome
import com.reddity.app.navigation.navigateToChat
import com.reddity.app.navigation.navigateToCreatePost
import com.reddity.app.navigation.navigateToExplore
import com.reddity.app.navigation.navigateToNotification
import com.reddity.app.ui.TopLevelDestination.CHAT
import com.reddity.app.ui.TopLevelDestination.CREATE_POST
import com.reddity.app.ui.TopLevelDestination.EXPLORE
import com.reddity.app.ui.TopLevelDestination.HOME
import com.reddity.app.ui.TopLevelDestination.NOTIFICATION
import com.reddity.app.ui.TopLevelDestination.values

@Composable
fun rememberRedditAppState(
    navController: NavHostController = rememberNavController()
): RedditAppState {
    return remember(navController) {
        RedditAppState(navController)
    }
}

@Stable
class RedditAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val topLevelDestinations: List<TopLevelDestination> = values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            HOME -> navController.navigateToHome(topLevelNavOptions)
            EXPLORE -> navController.navigateToExplore(topLevelNavOptions)
            CREATE_POST -> navController.navigateToCreatePost(topLevelNavOptions)
            CHAT -> navController.navigateToChat(topLevelNavOptions)
            NOTIFICATION -> navController.navigateToNotification(topLevelNavOptions)
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}

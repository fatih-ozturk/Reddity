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
package com.reddity.app.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
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

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun rememberReddityAppState(
    bottomSheetNavigator: BottomSheetNavigator = rememberBottomSheetNavigator(),
    navController: NavHostController = rememberNavController(bottomSheetNavigator),
    communitiesDrawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    profileDrawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
): ReddityAppState {
    return remember(
        navController,
        bottomSheetNavigator,
        communitiesDrawerState,
        profileDrawerState
    ) {
        ReddityAppState(
            navController,
            bottomSheetNavigator,
            communitiesDrawerState,
            profileDrawerState
        )
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Stable
class ReddityAppState(
    val navController: NavHostController,
    val bottomSheetNavigator: BottomSheetNavigator,
    val communitiesDrawerState: DrawerState,
    val profileDrawerState: DrawerState
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
}

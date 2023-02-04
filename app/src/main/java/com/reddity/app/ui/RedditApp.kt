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

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.reddity.app.home.navigation.homeNavigationRoute
import com.reddity.app.home.navigation.homeScreen
import com.reddity.app.login.navigation.loginScreen
import com.reddity.app.login.navigation.navigateToLogin
import com.reddity.app.navigation.chatScreen
import com.reddity.app.navigation.createPostScreen
import com.reddity.app.navigation.exploreScreen
import com.reddity.app.navigation.notificationScreen

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalLayoutApi::class,
    ExperimentalMaterialNavigationApi::class
)
@Composable
fun RedditApp(
    appState: RedditAppState = rememberRedditAppState()
) {
    ModalBottomSheetLayout(appState.bottomSheetNavigator) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                modifier = Modifier.semantics { testTagsAsResourceId = true },
                contentColor = MaterialTheme.colors.onBackground,
                bottomBar = {
                    RedditBottomNavigation(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentDestination
                    )
                }
            ) { padding ->
                RedditNavHost(
                    navController = appState.navController,
                    modifier = Modifier
                        .padding(padding)
                        .consumedWindowInsets(padding)
                )
            }
        }
    }
}

@Composable
fun RedditNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen(onLoginRequired = {
            navController.navigateToLogin()
        })
        exploreScreen()
        createPostScreen()
        chatScreen()
        notificationScreen()
        loginScreen()
    }
}

@Composable
fun RedditBottomNavigation(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = contentColorFor(MaterialTheme.colors.surface)
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            BottomNavigationItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Crossfade(targetState = selected) {
                        val icon = if (it) destination.selectedIcon else destination.unselectedIcon
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false

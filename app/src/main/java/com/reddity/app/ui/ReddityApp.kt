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
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
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
    ExperimentalMaterialNavigationApi::class,
    ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun ReddityApp(
    appState: ReddityAppState = rememberReddityAppState()
) {
    ModalBottomSheetLayout(appState.bottomSheetNavigator) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                modifier = Modifier.semantics { testTagsAsResourceId = true },
                bottomBar = {
                    ReddityBottomNavigation(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentDestination
                    )
                }
            ) { padding ->
                ReddityNavHost(
                    navController = appState.navController,
                    modifier = Modifier
                        .padding(padding)
                        .consumeWindowInsets(padding)
                )
            }
        }
    }
}

@Composable
fun ReddityNavHost(
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
fun ReddityBottomNavigation(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Crossfade(targetState = selected, label = "") {
                        val icon = if (it) destination.selectedIcon else destination.unselectedIcon
                        Icon(
                            modifier = Modifier.size(24.dp),
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

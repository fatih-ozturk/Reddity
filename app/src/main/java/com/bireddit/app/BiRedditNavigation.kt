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
package com.bireddit.app

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.DrawerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.bireddit.app.home.main.home.HomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Explore : Screen("following")
    object CreatePost : Screen("create_post")
    object Chat : Screen("chat")
    object Notification : Screen("notification")
}

private sealed class LeafScreen(
    private val route: String
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Home : LeafScreen("home")
    object Explore : LeafScreen("following")
    object CreatePost : LeafScreen("create_post")
    object Chat : LeafScreen("chat")
    object Notification : LeafScreen("notification")
}

@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    drawerProfileState: DrawerState
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        addHomeTopLevel(navController,drawerState,drawerProfileState)
        addExploreTopLevel(navController)
        addCreatePostTopLevel(navController)
        addChatTopLevel(navController)
        addNotificationTopLevel(navController)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addHomeTopLevel(
    navController: NavController,
    drawerState: DrawerState,
    drawerProfileState: DrawerState
) {
    navigation(
        route = Screen.Home.route,
        startDestination = LeafScreen.Home.createRoute(Screen.Home)
    ) {
        addListing(navController, Screen.Home,drawerState,drawerProfileState)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addExploreTopLevel(
    navController: NavController
) {
    navigation(
        route = Screen.Explore.route,
        startDestination = LeafScreen.Explore.createRoute(Screen.Explore)
    ) {
        addExplore(navController, Screen.Explore)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCreatePostTopLevel(
    navController: NavController
) {
    navigation(
        route = Screen.CreatePost.route,
        startDestination = LeafScreen.CreatePost.createRoute(Screen.CreatePost)
    ) {
        addCreatePost(navController, Screen.CreatePost)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addChatTopLevel(
    navController: NavController
) {
    navigation(
        route = Screen.Chat.route,
        startDestination = LeafScreen.Chat.createRoute(Screen.Chat)
    ) {
        addChat(navController, Screen.Chat)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addNotificationTopLevel(
    navController: NavController
) {
    navigation(
        route = Screen.Notification.route,
        startDestination = LeafScreen.Notification.createRoute(Screen.Notification)
    ) {
        addNotification(navController, Screen.Notification)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addListing(
    navController: NavController,
    root: Screen,
    drawerState: DrawerState,
    drawerProfileState: DrawerState
) {
    composable(
        route = LeafScreen.Home.createRoute(root)
    ) {
        HomeScreen(modifier = Modifier, navController = navController,drawerState,drawerProfileState)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addExplore(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.Explore.createRoute(root)
    ) {
        Text(modifier = Modifier.fillMaxSize(), text = "Explore Screen")
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCreatePost(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.CreatePost.createRoute(root)
    ) {
        Text(modifier = Modifier.fillMaxSize(), text = "Create Post Screen")
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addChat(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.Chat.createRoute(root)
    ) {
        Text(modifier = Modifier.fillMaxSize(), text = "Chat Screen")
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addNotification(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.Notification.createRoute(root)
    ) {
        Text(modifier = Modifier.fillMaxSize(), text = "Notification Screen")
    }
}

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
package com.reddity.app.old

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.reddity.app.R

@Composable
fun MainScreen() {
    val navController = rememberAnimatedNavController()
    val currentSelectedItem by navController.currentScreenAsState()
    Scaffold(
        bottomBar = {
            HomeBottomNavigation(
                modifier = Modifier.fillMaxWidth(),
                selectedNavigation = currentSelectedItem,
                onNavigationSelected = { selected ->
                    navController.navigate(selected.route) {
                        launchSingleTop = true
                        restoreState = true

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                }
            )
        }
    ) {
        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun HomeBottomNavigation(
    modifier: Modifier = Modifier,
    selectedNavigation: Screen,
    onNavigationSelected: (Screen) -> Unit
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = contentColorFor(MaterialTheme.colors.surface),
        modifier = modifier
    ) {
        HomeNavigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    HomeNavigationItemIcon(
                        item = item,
                        selected = selectedNavigation == item.screen
                    )
                },
                selected = selectedNavigation == item.screen,
                onClick = { onNavigationSelected(item.screen) }
            )
        }
    }
}

@Composable
fun HomeNavigationItemIcon(item: HomeNavigationItem.ResourceIcon, selected: Boolean) {
    val painter = painterResource(item.iconResId)

    val selectedPainter = item.selectedIconResId?.let { painterResource(it) }

    if (selectedPainter != null) {
        Crossfade(targetState = selected) {
            Icon(
                painter = if (it) selectedPainter else painter,
                contentDescription = stringResource(item.contentDescriptionResId)
            )
        }
    } else {
        Icon(
            painter = painter,
            contentDescription = stringResource(item.contentDescriptionResId)
        )
    }
}

private val HomeNavigationItems = listOf(
    HomeNavigationItem.ResourceIcon(
        screen = Screen.Home,
        contentDescriptionResId = R.string.navbar_home_title,
        iconResId = R.drawable.icon_home,
        selectedIconResId = R.drawable.icon_home_fill
    ),
    HomeNavigationItem.ResourceIcon(
        screen = Screen.Explore,
        contentDescriptionResId = R.string.navbar_home_explore,
        iconResId = R.drawable.icon_discover,
        selectedIconResId = R.drawable.icon_discover_fill
    ),
    HomeNavigationItem.ResourceIcon(
        screen = Screen.CreatePost,
        contentDescriptionResId = R.string.navbar_home_create_post,
        iconResId = R.drawable.icon_add
    ),
    HomeNavigationItem.ResourceIcon(
        screen = Screen.Chat,
        contentDescriptionResId = R.string.navbar_home_chat,
        iconResId = R.drawable.icon_chat,
        selectedIconResId = R.drawable.icon_chat_fill
    ),
    HomeNavigationItem.ResourceIcon(
        screen = Screen.Notification,
        contentDescriptionResId = R.string.navbar_home_notification,
        iconResId = R.drawable.icon_notification,
        selectedIconResId = R.drawable.icon_notification_fill
    )
)

sealed class HomeNavigationItem(
    val screen: Screen,
    @StringRes val contentDescriptionResId: Int
) {
    class ResourceIcon(
        screen: Screen,
        @StringRes contentDescriptionResId: Int,
        @DrawableRes val iconResId: Int,
        @DrawableRes val selectedIconResId: Int? = null
    ) : HomeNavigationItem(screen, contentDescriptionResId)
}

/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 */
@Stable
@Composable
private fun NavController.currentScreenAsState(): State<Screen> {
    val selectedItem = remember { mutableStateOf<Screen>(Screen.Home) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == Screen.Home.route } -> {
                    selectedItem.value = Screen.Home
                }
                destination.hierarchy.any { it.route == Screen.Explore.route } -> {
                    selectedItem.value = Screen.Explore
                }
                destination.hierarchy.any { it.route == Screen.CreatePost.route } -> {
                    selectedItem.value = Screen.CreatePost
                }
                destination.hierarchy.any { it.route == Screen.Chat.route } -> {
                    selectedItem.value = Screen.Chat
                }
                destination.hierarchy.any { it.route == Screen.Notification.route } -> {
                    selectedItem.value = Screen.Notification
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}

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
package com.reddity.app.home.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.DrawerState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.reddity.app.home.main.tabs.home.HomeTabScreen
import com.reddity.app.home.main.tabs.popular.PopularTabScreen
import com.reddity.app.home.main.views.HomeSearchView
import com.reddity.app.home.main.views.HomeTabView
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    onFilterViewClicked: () -> Unit,
    drawerState: DrawerState,
    drawerProfileState: DrawerState
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    Column {
        Surface(
            modifier = modifier,
            elevation = 4.dp
        ) {
            Column {
                HomeToolbar(
                    coroutineScope = coroutineScope,
                    drawerState = drawerState,
                    drawerProfileState = drawerProfileState
                )
                HomeTabView(
                    pagerState = pagerState,
                    coroutineScope = coroutineScope
                )
            }
        }
        HomeContent(
            pagerState = pagerState,
            onFilterViewClicked = onFilterViewClicked
        )
    }
}

@Composable
fun HomeToolbar(
    coroutineScope: CoroutineScope,
    drawerState: DrawerState,
    drawerProfileState: DrawerState
) {
    HomeSearchView(onMenuClicked = {
        coroutineScope.launch {
            drawerState.open()
        }
    }, onProfileClicked = {
        coroutineScope.launch {
            drawerProfileState.open()
        }
    })
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeContent(
    pagerState: PagerState,
    onFilterViewClicked: () -> Unit
) {
    HorizontalPager(
        modifier = Modifier,
        verticalAlignment = Alignment.Top,
        state = pagerState,
        count = 2
    ) { page ->
        when (page) {
            0 -> HomeTabScreen()
            1 -> PopularTabScreen(onFilterViewClicked)
        }
    }
}

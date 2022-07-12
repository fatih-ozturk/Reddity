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
package com.bireddit.app.home.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bireddit.app.home.main.listing.ListingFilterView
import com.bireddit.app.home.main.tabs.home.HomeTabHomeScreen
import com.bireddit.app.home.main.views.HomeSearchView
import com.bireddit.app.home.main.views.HomeTabView
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
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerProfileState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val pagerState = rememberPagerState()
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalDrawer(drawerState = drawerProfileState, drawerContent = {
            Box(contentAlignment = Alignment.Center) {
                Text(text = "PROFILE")
            }
        }) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                ModalDrawer(drawerState = drawerState, drawerContent = {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "MENU")
                    }
                }) {
                    Column {
                        Surface(
                            modifier = Modifier,
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
                        HomeContent(pagerState = pagerState)
                    }
                }
            }
        }
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
    pagerState: PagerState
) {
    HorizontalPager(
        modifier = Modifier,
        verticalAlignment = Alignment.Top,
        state = pagerState,
        count = 2
    ) { page ->
        when (page) {
            0 -> HomeTabHomeScreen()
            1 -> ListingFilterView()
        }
    }
}

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
package com.reddity.app.home.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.reddity.app.home.tabs.home.HomeTabScreen
import com.reddity.app.home.tabs.popular.PopularTabScreen
import com.reddity.app.home.views.HomeSearchView
import com.reddity.app.home.views.HomeTabView

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    onLoginRequired: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    val toolbarUiState by viewModel.toolbarUiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Column {
                HomeSearchView(toolbarUiState = toolbarUiState)
                HomeTabView(pagerState = pagerState, coroutineScope = coroutineScope)
                Divider()
            }
        }
    ) {
        HomeContent(
            modifier = Modifier.padding(it),
            pagerState = pagerState,
            onLoginRequired = onLoginRequired
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeContent(
    modifier: Modifier,
    pagerState: PagerState,
    onLoginRequired: () -> Unit = {}
) {
    HorizontalPager(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        state = pagerState,
        count = 2
    ) { page ->
        when (page) {
            0 -> HomeTabScreen(
                onLoginRequired = onLoginRequired
            )
            1 -> PopularTabScreen(
                onLoginRequired = onLoginRequired
            )
        }
    }
}

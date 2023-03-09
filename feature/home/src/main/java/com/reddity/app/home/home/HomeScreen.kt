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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.reddity.app.home.tabs.home.HomeTabScreen
import com.reddity.app.home.tabs.popular.PopularTabScreen
import com.reddity.app.ui.commons.customTabIndicatorOffset
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onLoginRequired: () -> Unit = {},
    openProfileDrawer: () -> Unit = {},
    openCommunityDrawer: () -> Unit = {}
) {
    val viewModel: HomeViewModel = hiltViewModel()

    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current
    val tabs = listOf("Home", "Popular")

    val toolbarUiState by viewModel.toolbarUiState.collectAsStateWithLifecycle()
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val popularTabLazyListState = rememberLazyListState()
    val homeTabLazyListState = rememberLazyListState()

    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .semantics { isContainer = true }
                .zIndex(1f)
                .fillMaxWidth()
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.TopCenter),
                query = text,
                onQueryChange = { text = it },
                onSearch = {
                    focusManager.clearFocus()
                    active = false
                },
                active = active,
                onActiveChange = {
                    active = it
                    if (!active) focusManager.clearFocus()
                },
                placeholder = { Text("Search", style = MaterialTheme.typography.bodyLarge) },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            when (active) {
                                true -> {
                                    focusManager.clearFocus()
                                    active = false
                                    text = ""
                                }

                                false -> {
                                    openCommunityDrawer()
                                }
                            }
                        },
                        imageVector = if (active) Icons.Default.ArrowBack else Icons.Default.Menu,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (active) {
                        Icon(
                            modifier = Modifier.clickable {
                                text = ""
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    } else {
                        val painter = rememberVectorPainter(image = Icons.Default.Person)
                        AsyncImage(
                            model = toolbarUiState.redditUser?.avatarUrl,
                            placeholder = painter,
                            error = painter,
                            fallback = painter,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    openProfileDrawer()
                                },
                            alignment = Alignment.CenterEnd,
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            ) {
                // TODO search suggestion
            }
        }
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.customTabIndicatorOffset(
                        currentTabPosition = tabPositions[pagerState.currentPage],
                        tabWidth = tabWidths[pagerState.currentPage]
                    )
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier,
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                            if (pagerState.currentPage == index) homeTabLazyListState.animateScrollToItem(
                                0
                            )
                        }
                    },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall,
                            onTextLayout = { textLayoutResult ->
                                tabWidths[index] =
                                    with(density) { textLayoutResult.size.width.toDp() }
                            },
                            color = if (pagerState.currentPage == index) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                )
            }
        }
        HorizontalPager(
            modifier = Modifier,
            verticalAlignment = Alignment.Top,
            state = pagerState,
            pageCount = 2,
            beyondBoundsPageCount = 2
        ) { page ->
            when (page) {
                0 -> HomeTabScreen(
                    listState = homeTabLazyListState
                )

                1 -> PopularTabScreen(
                    onLoginRequired = onLoginRequired,
                    listState = popularTabLazyListState
                )
            }
        }
    }
}

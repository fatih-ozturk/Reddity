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
package com.reddity.app.home.tabs.popular

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.reddity.app.home.listing.ListingItemView
import com.reddity.app.ui.widget.FeedLoadingIcon

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PopularTabScreen(
    viewModel: PopularTabViewModel = hiltViewModel(),
    onLoginRequired: () -> Unit = {}
) {
    val items = viewModel.feed.collectAsLazyPagingItems()
    val isRefreshing by remember { derivedStateOf { items.loadState.refresh is LoadState.Loading } }
    val state = rememberPullRefreshState(
        refreshing = items.loadState.refresh is LoadState.Loading,
        onRefresh = {
            items.refresh()
        }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        Modifier
            .pullRefresh(state)
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(
                items = items,
                key = { _, item ->
                    item.id
                }
            ) { _, item ->
                if (item == null) return@itemsIndexed
                ListingItemView(
                    post = item,
                    onVoteClicked = {
                        viewModel.onVoteClicked(item.id, it)
                    },
                    authState = uiState.authState,
                    onLoginRequired = onLoginRequired
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(color = Color(237, 239, 247, 255))
                )
            }
        }
        FeedLoadingIcon(isRefreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

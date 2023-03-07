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
package com.reddity.app.home.tabs.popular

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.reddity.app.home.listing.ReddityPostListingView
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PopularTabScreen(
    viewModel: PopularTabViewModel = hiltViewModel(),
    onLoginRequired: () -> Unit = {},
    listState: LazyListState
) {
    ReddityPostListingView(
        items = viewModel.feed.collectAsLazyPagingItems(),
        onVoteClicked = { postId, vote ->
            viewModel.onVoteClicked(postId, vote)
        },
        listState = listState
    )

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                PopularTabEvent.LoginRequired -> onLoginRequired()
            }
        }
    }
}

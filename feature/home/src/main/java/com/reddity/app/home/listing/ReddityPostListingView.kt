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
package com.reddity.app.home.listing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.reddity.app.home.listing.item.ListingItemCardView
import com.reddity.app.model.Post
import com.reddity.app.model.PostVoteStatus

@Composable
fun ReddityPostListingView(
    items: LazyPagingItems<Post>,
    onVoteClicked: (postId: String, vote: PostVoteStatus) -> Unit,
    listState: LazyListState
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState
    ) {
        items(items = items, key = { item ->
            item.id
        }) { item ->
            if (item == null) return@items
            ListingItemCardView(
                post = item,
                onVoteClicked = {
                    onVoteClicked(item.id, it)
                }
            )
        }
    }
}

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
package com.reddity.app.home.listing.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.reddity.app.home.listing.item.body.ListingItemBodyView
import com.reddity.app.home.listing.item.footer.ListingItemFooterView
import com.reddity.app.home.listing.item.header.ListingItemHeader
import com.reddity.app.model.Post
import com.reddity.app.model.PostVoteStatus

@Composable
fun ListingItemCardView(
    post: Post,
    onVoteClicked: (PostVoteStatus) -> Unit = {},
    onCommentClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {}
) {
    OutlinedCard(
        Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        ListingItemHeader(post)
        ListingItemBodyView(post)
        ListingItemFooterView(
            post = post,
            onVoteClicked = onVoteClicked,
            onCommentClicked = onCommentClicked,
            onShareClicked = onShareClicked
        )
    }
}

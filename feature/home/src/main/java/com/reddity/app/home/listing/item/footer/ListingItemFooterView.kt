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
package com.reddity.app.home.listing.item.footer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.reddity.app.home.listing.item.footer.parts.ItemCommentView
import com.reddity.app.home.listing.item.footer.parts.ItemShareView
import com.reddity.app.home.listing.item.footer.parts.ItemVoteView
import com.reddity.app.model.Post
import com.reddity.app.model.PostVoteStatus

@Composable
fun ListingItemFooterView(
    post: Post,
    onVoteClicked: (PostVoteStatus) -> Unit = {},
    onCommentClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ItemVoteView(
            postVoteCount = post.voteCount,
            postVoteStatus = post.postVoteStatus,
            onVoteClicked = onVoteClicked
        )
        Spacer(modifier = Modifier.width(32.dp))
        ItemCommentView(
            commentCount = post.commentCount,
            onCommentClicked = onCommentClicked
        )
        Spacer(modifier = Modifier.width(32.dp))
        ItemShareView(
            onShareClicked = onShareClicked
        )
    }
}

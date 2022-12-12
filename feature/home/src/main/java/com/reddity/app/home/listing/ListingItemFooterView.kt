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
package com.reddity.app.home.listing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.reddity.app.home.listing.footer.ItemCommentView
import com.reddity.app.home.listing.footer.ItemGiveAwardView
import com.reddity.app.home.listing.footer.ItemShareView
import com.reddity.app.home.listing.footer.ItemVoteView
import com.reddity.app.model.Post
import com.reddity.app.model.PostVoteStatus

@Composable
fun ListingItemFooterView(
    post: Post,
    onVoteClicked: (PostVoteStatus) -> Unit = {},
    onCommentClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {},
    onGiveAwardClicked: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .padding(start = 16.dp, end = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ItemVoteView(
            voteCount = post.voteCount,
            voteStatus = post.postVoteStatus,
            onVoteClicked = onVoteClicked,
        )
        ItemCommentView(
            commentCount = post.commentCount,
            onCommentClicked = onCommentClicked
        )
        ItemShareView(
            onShareClicked = onShareClicked
        )
        ItemGiveAwardView(
            onGiveAwardClicked = onGiveAwardClicked
        )
    }
}

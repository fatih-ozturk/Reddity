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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.exoplayer2.ExoPlayer
import com.reddity.app.model.Post
import com.reddity.app.model.PostVoteStatus
import com.reddity.app.model.ReddityAuthState

@Composable
fun ListingItemView(
    post: Post,
    onVoteClicked: (PostVoteStatus) -> Unit = {},
    onCommentClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {},
    onGiveAwardClicked: () -> Unit = {},
    onLoginRequired: () -> Unit = {},
    authState: ReddityAuthState,
    exoPlayer: ExoPlayer,
    isVideoPlaying: Boolean
) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
        ) {
            ListingItemHeaderView(post)
            ListingItemBodyView(post, exoPlayer, isVideoPlaying)
            ListingItemFooterView(
                post = post,
                onVoteClicked = onVoteClicked,
                onShareClicked = onShareClicked,
                onCommentClicked = onCommentClicked,
                onGiveAwardClicked = onGiveAwardClicked,
                onLoginRequired = onLoginRequired,
                authState = authState
            )
        }
    }
}

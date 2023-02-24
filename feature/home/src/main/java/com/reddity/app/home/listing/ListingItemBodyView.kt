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

import androidx.compose.runtime.Composable
import com.google.android.exoplayer2.ExoPlayer
import com.reddity.app.home.listing.body.ItemBodyImageView
import com.reddity.app.home.listing.body.ItemBodyLinkView
import com.reddity.app.home.listing.body.ItemBodySelfView
import com.reddity.app.home.listing.body.ItemBodyVideoView
import com.reddity.app.model.Post
import com.reddity.app.model.PostType

@Composable
fun ListingItemBodyView(
    post: Post,
    exoPlayer: ExoPlayer,
    isVideoPlaying: Boolean
) {
    when (post.postType) {
        PostType.IMAGE -> {
            ItemBodyImageView(
                title = post.title,
                image = post.image,
                awardCount = post.awardsCount,
                awardList = post.awardsIconList
            )
        }

        PostType.LINK -> {
            ItemBodyLinkView(
                title = post.title,
                image = post.image,
                awardCount = post.awardsCount,
                awardList = post.awardsIconList
            )
        }

        PostType.HOSTED_VIDEO, PostType.RICH_VIDEO -> {
            ItemBodyVideoView(
                awardCount = post.awardsCount,
                awardList = post.awardsIconList,
                exoPlayer = exoPlayer,
                isVideoPlaying = isVideoPlaying,
                thumbnailUrl = post.videoThumbnail
            )
        }

        PostType.SELF -> {
            ItemBodySelfView(
                title = post.title,
                content = post.content,
                awardCount = post.awardsCount,
                awardList = post.awardsIconList
            )
        }
    }
}

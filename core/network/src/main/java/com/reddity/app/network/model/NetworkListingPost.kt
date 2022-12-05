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
package com.reddity.app.network.model

import com.reddity.app.network.model.image.NetworkImagePreview
import com.reddity.app.network.model.media.NetworkMedia
import com.squareup.moshi.Json

data class NetworkListingPost(
    @Json(name = "created_utc") val created: Int,
    @Json(name = "downs") val downvoteCount: Int?,
    @Json(name = "num_comments") val commentCount: Int,
    @Json(name = "selftext") val content: String?,
    @Json(name = "ups") val upvoteCount: Int,
    val author: String,
    val id: String,
    val name: String,
    val kind: String?,
    @Json(name = "post_hint") val postHint: String?,
    @Json(name = "is_video") val isVideo: Boolean?,
    @Json(name = "is_self") val isSelf: Boolean?,
    val preview: NetworkImagePreview?,
    val subreddit: String,
    val title: String,
    val media: NetworkMedia?,
    @Json(name = "sr_detail") val subredditDetail: NetworkSubredditDetail?,
    @Json(name = "all_awardings") val awards: List<NetworkAwards>,
    @Json(name = "total_awards_received") val awardsCount: Int
) {
    val postType: NetworkListingPostType
        get() = when {
            postHint != null -> NetworkListingPostType.of(postHint)
            isSelf == true -> NetworkListingPostType.SELF
            isVideo == true && media != null -> NetworkListingPostType.HOSTED_VIDEO
            else -> NetworkListingPostType.SELF
        }

    val awardsIconList: List<String>
        get() = awards.map { it.resizedIcons }.map { it[2].url.orEmpty() }
}

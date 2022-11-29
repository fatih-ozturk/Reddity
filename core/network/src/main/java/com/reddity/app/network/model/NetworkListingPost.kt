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
    @Json(name = "created_utc") val created: Int?,
    @Json(name = "downs") val downvoteCount: Int?,
    @Json(name = "num_comments") val commentCount: Int?,
    @Json(name = "selftext") val content: String?,
    @Json(name = "ups") val upvoteCount: Int?,
    val author: String?,
    val id: String?,
    val kind: String?,
    val postType: NetworkListingPostType?,
    val preview: NetworkImagePreview?,
    val subreddit: String?,
    val title: String?,
    val media: NetworkMedia?
)

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
package com.reddity.app.network.model.response.posts

import android.os.Parcelable
import com.reddity.app.model.Post
import com.reddity.app.model.PostVoteStatus
import com.reddity.app.network.model.response.posts.enums.NetworkListingPostType
import com.reddity.app.network.model.response.posts.image.NetworkPostImagePreview
import com.reddity.app.network.model.response.posts.media.NetworkPostMedia
import com.reddity.app.network.model.response.posts.postdetail.NetworkSubredditDetail
import com.reddity.app.network.model.response.posts.postdetail.NetworkUserAwards
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class NetworkPostData(
    @Json(name = "created_utc") val created: Int,
    @Json(name = "downs") val downvoteCount: Int?,
    @Json(name = "num_comments") val commentCount: Int,
    @Json(name = "selftext") val content: String?,
    @Json(name = "ups") val upvoteCount: Int,
    @Json(name = "author") val author: String,
    @Json(name = "name") val id: String,
    @Json(name = "kind") val kind: String?,
    @Json(name = "post_hint") val postHint: String?,
    @Json(name = "is_video") val isVideo: Boolean?,
    @Json(name = "is_self") val isSelf: Boolean?,
    @Json(name = "preview") val preview: NetworkPostImagePreview?,
    @Json(name = "subreddit") val subreddit: String,
    @Json(name = "title") val title: String,
    @Json(name = "media") val media: NetworkPostMedia?,
    @Json(name = "sr_detail") val subredditDetail: NetworkSubredditDetail?,
    @Json(name = "all_awardings") val awards: List<NetworkUserAwards>,
    @Json(name = "total_awards_received") val awardsCount: Int,
    @Json(name = "likes") val likes: Boolean?
) : Parcelable {
    val postType: NetworkListingPostType
        get() = when {
            postHint != null -> NetworkListingPostType.of(postHint)
            isSelf == true -> NetworkListingPostType.SELF
            isVideo == true && media != null -> NetworkListingPostType.HOSTED_VIDEO
            else -> NetworkListingPostType.SELF
        }

    val awardsIconList: List<String>
        get() = awards.map { it.resizedIcons }.map { it[2].url.orEmpty() }

    val postVoteStatus: PostVoteStatus
        get() = when (likes) {
            true -> PostVoteStatus.UPVOTE
            false -> PostVoteStatus.DOWN_VOTE
            null -> PostVoteStatus.NONE
        }
}

fun NetworkPostData.asExternal(): Post = Post(
    id = id,
    postType = enumValueOf(postType.name),
    author = author,
    subreddit = subreddit,
    subredditIconUrl = subredditDetail?.subredditIconUrl,
    title = title,
    content = content,
    image = preview?.images?.firstOrNull()?.source?.url,
    timestamp = created,
    videoUrl = media?.redditVideo?.videoUrl,
    videoThumbnail = preview?.images?.firstOrNull()?.source?.url,
    voteCount = upvoteCount,
    commentCount = commentCount,
    awardsCount = awardsCount,
    awardsIconList = awardsIconList,
    postVoteStatus = postVoteStatus
)

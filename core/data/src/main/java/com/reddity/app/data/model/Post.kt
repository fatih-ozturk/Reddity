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
package com.reddity.app.data.model

import com.reddity.app.database.entity.RedditPostsEntity
import com.reddity.app.network.model.NetworkListingPost

fun NetworkListingPost.asEntity(): RedditPostsEntity = RedditPostsEntity(
    id = name,
    postType = enumValueOf(postType.name),
    author = author,
    subreddit = subreddit,
    subredditIconUrl = subredditDetail?.subredditIconUrl,
    title = title,
    content = content,
    image = preview?.images?.firstOrNull()?.source?.url,
    timestamp = created,
    videoUrl = media?.redditVideo?.videoUrl,
    voteCount = upvoteCount,
    commentCount = commentCount,
    awardsCount = awardsCount,
    awardsIconList = awardsIconList
)

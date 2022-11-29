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
package com.reddity.app.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reddity.app.model.Post
import com.reddity.app.model.PostType

@Entity(tableName = "redditPosts")
data class RedditPostsEntity(
    @PrimaryKey val id: String,
    val postId: String,
    val postType: PostType,
    val author: String,
    val subreddit: String,
    val title: String,
    val content: String?,
    val image: String?,
    val linkImage: String?,
    // val awards: List<String>?,
    val timestamp: String,
    val videoUrl: String?,
    val voteCount: String,
    val commentCount: String
)

fun RedditPostsEntity.asExternalModel(): Post = Post(
    id = id,
    postType = postType,
    author = author,
    subreddit = subreddit,
    title = title,
    content = content,
    image = image,
    linkImage = linkImage,
    awards = null,
    timestamp = timestamp,
    videoUrl = videoUrl,
    voteCount = voteCount,
    commentCount = commentCount
)

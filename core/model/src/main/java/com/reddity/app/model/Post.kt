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
package com.reddity.app.model

data class Post(
    val id: String,
    val postType: PostType,
    val author: String,
    val subreddit: String,
    val subredditIconUrl: String?,
    val title: String,
    val content: String?,
    val image: String?,
    val timestamp: Int,
    val videoUrl: String?,
    val videoThumbnail: String?,
    val voteCount: Int,
    val commentCount: Int,
    val awardsCount: Int,
    val awardsIconList: List<String>,
    val postVoteStatus: PostVoteStatus
)

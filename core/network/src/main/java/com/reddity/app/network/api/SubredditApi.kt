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
package com.reddity.app.network.api

import com.reddity.app.network.model.response.subreddit.EnvelopedSubredditDataListing
import retrofit2.http.GET
import retrofit2.http.Query

interface SubredditApi {

    @GET("/subreddits/mine/subscriber")
    suspend fun getFollowedSubreddits(
        @Query("raw_json") rawJson: Int = 1
    ): EnvelopedSubredditDataListing
}

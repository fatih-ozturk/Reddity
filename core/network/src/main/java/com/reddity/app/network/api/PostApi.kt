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
package com.reddity.app.network.api

import com.reddity.app.network.model.NetworkListingResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApi {

    @GET("/hot.json")
    suspend fun getPopularPostList(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("raw_json") rawJson: Int = 1,
        @Query("count") loadSize: Int = 0,
        @Query("sr_detail") includeSrDetail: Boolean = true
    ): NetworkListingResponse

    @GET("/by_id/{postId}")
    suspend fun getRedditPostById(
        @Path("postId") postId: String,
        @Query("raw_json") rawJson: Int = 1,
        @Query("sr_detail") includeSrDetail: Boolean = true
    ): NetworkListingResponse

    @POST("/api/vote")
    suspend fun vote(
        @Query("raw_json") rawJson: Int = 1,
        @Query("id") postId: String,
        @Query("dir") dir: Int
    ): Any
}

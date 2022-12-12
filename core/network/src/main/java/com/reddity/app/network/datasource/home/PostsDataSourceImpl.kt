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
package com.reddity.app.network.datasource.home

import com.reddity.app.network.api.PostApi
import com.reddity.app.network.model.NetworkListingDataResponse
import com.reddity.app.network.model.request.NetworkVoteRequest
import it.czerwinski.android.hilt.annotations.Bound
import javax.inject.Inject
import javax.inject.Singleton

@Bound
@Singleton
class PostsDataSourceImpl @Inject constructor(
    private val postApi: PostApi
) : PostsDataSource {
    override suspend fun getPopularPostList(
        loadSize: Int,
        after: String?,
        before: String?
    ): NetworkListingDataResponse = postApi.getPopularPostList(
        loadSize = loadSize, after = after, before = before
    ).data

    override suspend fun postVote(
        postId: String,
        request: NetworkVoteRequest
    ) = postApi.vote(postId = postId, dir = request.value)

    override suspend fun getPostById(
        postId: String
    ): NetworkListingDataResponse = postApi.getRedditPostById(postId = postId).data
}

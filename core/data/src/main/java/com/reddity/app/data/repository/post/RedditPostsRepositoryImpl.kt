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
package com.reddity.app.data.repository.post

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.reddity.app.data.mediators.PostsPageKeyedRemotePagingSource
import com.reddity.app.model.Post
import com.reddity.app.model.PostVoteStatus
import com.reddity.app.model.Result
import com.reddity.app.network.datasource.home.PostsDataSource
import com.reddity.app.network.model.request.NetworkVoteRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RedditPostsRepositoryImpl @Inject constructor(
    private val postsDataSource: PostsDataSource
) : RedditPostsRepository {

    override fun getHomePopularPagingData(): Flow<PagingData<Post>> =
        Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = true),
            pagingSourceFactory = { PostsPageKeyedRemotePagingSource(postsDataSource) }
        ).flow

    override suspend fun postVote(
        postId: String,
        request: PostVoteStatus
    ): Result<Unit> {
        return try {
            postsDataSource.postVote(postId = postId, request = NetworkVoteRequest.of(request.name))
            Result.Success(Unit)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}

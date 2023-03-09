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
import com.reddity.app.data.mediators.HomePostsPagingSource
import com.reddity.app.data.mediators.PopularPostsPagingSource
import com.reddity.app.model.Post
import com.reddity.app.model.PostVoteStatus
import com.reddity.app.network.datasource.home.PostsDataSource
import com.reddity.app.network.model.request.NetworkPostVoteRequest
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

internal class RedditPostsRepositoryImpl @Inject constructor(
    private val postsDataSource: PostsDataSource
) : RedditPostsRepository {

    override fun getPopularPagingData(): Flow<PagingData<Post>> =
        Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = true),
            pagingSourceFactory = { PopularPostsPagingSource(postsDataSource) }
        ).flow

    override fun getHomePagingData(): Flow<PagingData<Post>> =
        Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = true),
            pagingSourceFactory = { HomePostsPagingSource(postsDataSource) }
        ).flow

    override suspend fun postVote(
        postId: String,
        request: PostVoteStatus
    ) {
        try {
            postsDataSource.postVote(postId = postId, request = NetworkPostVoteRequest.of(request.name))
        } catch (exception: Exception) {
            Timber.e(exception)
        }
    }
}

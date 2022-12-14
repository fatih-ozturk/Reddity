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
package com.reddity.app.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.reddity.app.data.mediators.PostsPageKeyedRemoteMediator
import com.reddity.app.data.model.asEntity
import com.reddity.app.database.dao.RedditPostsDao
import com.reddity.app.database.entity.RedditPostsEntity
import com.reddity.app.database.entity.asExternalModel
import com.reddity.app.model.Post
import com.reddity.app.model.PostVoteStatus
import com.reddity.app.model.Result
import com.reddity.app.network.datasource.home.PostsDataSource
import com.reddity.app.network.model.request.NetworkVoteRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RedditPostsRepositoryImpl @Inject constructor(
    private val postsPageKeyedRemoteMediator: PostsPageKeyedRemoteMediator,
    private val redditPostsDao: RedditPostsDao,
    private val postsDataSource: PostsDataSource
) : RedditPostsRepository {

    override fun getHomePopularPagingData(): Flow<PagingData<Post>> =
        Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = true),
            remoteMediator = postsPageKeyedRemoteMediator,
            pagingSourceFactory = {
                redditPostsDao.getRedditPosts()
            }
        ).flow.map { pagingData ->
            pagingData.map(RedditPostsEntity::asExternalModel)
        }

    override suspend fun postVote(
        postId: String,
        request: PostVoteStatus
    ): Result<Unit> {
        return try {
            postsDataSource.postVote(postId = postId, request = NetworkVoteRequest.of(request.name))

            val post = postsDataSource.getPostById(postId = postId).children.first().data.asEntity()
            redditPostsDao.update(post)
            Result.Success(Unit)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }
}

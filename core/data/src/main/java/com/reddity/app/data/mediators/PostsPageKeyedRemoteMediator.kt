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
package com.reddity.app.data.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.reddity.app.data.model.asEntity
import com.reddity.app.database.ReddityDatabase
import com.reddity.app.database.dao.RedditPageKeysDao
import com.reddity.app.database.dao.RedditPostsDao
import com.reddity.app.database.entity.RedditPageKeysEntity
import com.reddity.app.database.entity.RedditPostsEntity
import com.reddity.app.network.datasource.home.HomeDataSource
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PostsPageKeyedRemoteMediator @Inject constructor(
    private val redditPageKeysDao: RedditPageKeysDao,
    private val redditPostsDao: RedditPostsDao,
    private val homeDataSource: HomeDataSource,
    private val reddityDatabase: ReddityDatabase
) : RemoteMediator<Int, RedditPostsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RedditPostsEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {

                    val remoteKey = reddityDatabase.withTransaction {
                        redditPageKeysDao.getRedditKeys()
                    }

                    if (remoteKey.first().after == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.first()
                }
            }

            val response = homeDataSource.getPopularPostList(
                loadSize = state.config.pageSize,
                after = loadKey?.after
            )

            reddityDatabase.withTransaction {
                redditPageKeysDao.insert(
                    RedditPageKeysEntity(id = 0, after = response.after)
                )
                val items = response.children.map { it.data.asEntity() }
                redditPostsDao.insertAll(items)
            }
            MediatorResult.Success(endOfPaginationReached = response.after.isNullOrEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}

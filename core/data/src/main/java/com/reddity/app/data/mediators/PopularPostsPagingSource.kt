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

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.reddity.app.model.Post
import com.reddity.app.network.datasource.home.PostsDataSource
import com.reddity.app.network.model.response.posts.asExternal
import javax.inject.Inject

class PopularPostsPagingSource @Inject constructor(
    private val postsDataSource: PostsDataSource
) : PagingSource<Pair<String?, String?>, Post>() {

    override val keyReuseSupported: Boolean = true

    override suspend fun load(params: LoadParams<Pair<String?, String?>>): LoadResult<Pair<String?, String?>, Post> {
        return try {
            val (before, after) = params.key ?: Pair(null, null)

            val response = postsDataSource.getPopularPostList(
                loadSize = params.loadSize,
                before = if (params is LoadParams.Prepend) before else null,
                after = if (params is LoadParams.Append) after else null
            )

            val items = response.children.map { it.data.asExternal() }

            LoadResult.Page(
                data = items,
                prevKey = Pair(before, after),
                nextKey = Pair(response.before, response.after)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Pair<String?, String?>, Post>): Pair<String?, String?>? {
        return null
    }
}

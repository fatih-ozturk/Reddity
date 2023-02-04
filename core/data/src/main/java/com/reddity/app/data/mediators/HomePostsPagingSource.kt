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
import com.reddity.app.network.model.asExternal
import javax.inject.Inject

class HomePostsPagingSource @Inject constructor(
    private val postsDataSource: PostsDataSource
) : PagingSource<String, Post>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
        return try {
            //TODO next page not loading properly
            val response = postsDataSource.getHomePostList(
                loadSize = params.loadSize,
                before = if (params is LoadParams.Prepend) params.key else null,
                after = if (params is LoadParams.Append) params.key else null
            )

            val items = response.children.map { it.data.asExternal() }

            LoadResult.Page(
                data = items,
                prevKey = response.before,
                nextKey = response.after
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Post>): String? {
        return null
    }
}

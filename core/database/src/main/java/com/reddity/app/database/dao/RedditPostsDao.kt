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
package com.reddity.app.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.reddity.app.database.entity.RedditPostsEntity

@Dao
interface RedditPostsDao {

    @Insert
    suspend fun insert(entity: RedditPostsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<RedditPostsEntity>)

    @Query("SELECT * FROM redditPosts")
    fun getRedditPosts(): PagingSource<Int, RedditPostsEntity>

    @Query("SELECT * FROM redditPosts WHERE id = :postId")
    suspend fun getPostById(postId: String): RedditPostsEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: RedditPostsEntity)

    @Delete
    suspend fun deleteRedditPost(entity: RedditPostsEntity): Int

    @Query("DELETE FROM redditPosts")
    suspend fun deleteAll()
}

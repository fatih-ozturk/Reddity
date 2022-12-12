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
package com.reddity.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.reddity.app.database.converters.InstantConverter
import com.reddity.app.database.converters.ListConverter
import com.reddity.app.database.converters.PostTypeConverter
import com.reddity.app.database.converters.PostVoteConverter
import com.reddity.app.database.dao.RedditPageKeysDao
import com.reddity.app.database.dao.RedditPostsDao
import com.reddity.app.database.entity.RedditPageKeysEntity
import com.reddity.app.database.entity.RedditPostsEntity

@Database(
    version = 3,
    entities = [RedditPageKeysEntity::class, RedditPostsEntity::class],
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
    PostTypeConverter::class,
    ListConverter::class,
    PostVoteConverter::class
)
abstract class ReddityDatabase : RoomDatabase() {
    abstract fun pageKeysDao(): RedditPageKeysDao
    abstract fun postsDao(): RedditPostsDao
}

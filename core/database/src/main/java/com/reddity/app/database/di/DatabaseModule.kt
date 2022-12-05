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
package com.reddity.app.database.di

import android.content.Context
import androidx.room.Room
import com.reddity.app.database.ReddityDatabase
import com.reddity.app.database.dao.RedditPageKeysDao
import com.reddity.app.database.dao.RedditPostsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideReddityDatabase(
        @ApplicationContext context: Context
    ): ReddityDatabase = Room.databaseBuilder(
        context, ReddityDatabase::class.java, "reddity-database"
    ).build()

    @Provides
    @Singleton
    fun providePageKeysDao(
        database: ReddityDatabase
    ): RedditPageKeysDao = database.pageKeysDao()

    @Provides
    @Singleton
    fun providePostsDao(
        database: ReddityDatabase
    ): RedditPostsDao = database.postsDao()
}

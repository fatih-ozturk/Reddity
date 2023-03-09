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
package com.reddity.app.network.di

import com.reddity.app.network.api.AccountApi
import com.reddity.app.network.api.PostApi
import com.reddity.app.network.api.SubredditApi
import com.reddity.app.network.datasource.account.AccountDataSource
import com.reddity.app.network.datasource.account.AccountDataSourceImpl
import com.reddity.app.network.datasource.home.PostsDataSource
import com.reddity.app.network.datasource.home.PostsDataSourceImpl
import com.reddity.app.network.datasource.subreddit.SubredditDataSource
import com.reddity.app.network.datasource.subreddit.SubredditDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun providePostDataSource(
        postApi: PostApi
    ): PostsDataSource {
        return PostsDataSourceImpl(postApi)
    }

    @Singleton
    @Provides
    fun provideAccountDataSource(
        accountApi: AccountApi
    ): AccountDataSource {
        return AccountDataSourceImpl(accountApi)
    }

    @Singleton
    @Provides
    fun provideSubredditDataSource(
        subredditApi: SubredditApi
    ): SubredditDataSource {
        return SubredditDataSourceImpl(subredditApi)
    }
}

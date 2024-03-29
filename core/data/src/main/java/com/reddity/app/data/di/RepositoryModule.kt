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
package com.reddity.app.data.di

import com.reddity.app.data.repository.account.RedditAccountRepository
import com.reddity.app.data.repository.account.RedditAccountRepositoryImpl
import com.reddity.app.data.repository.post.RedditPostsRepository
import com.reddity.app.data.repository.post.RedditPostsRepositoryImpl
import com.reddity.app.data.repository.subreddit.RedditSubredditRepository
import com.reddity.app.data.repository.subreddit.RedditSubredditRepositoryImpl
import com.reddity.app.network.datasource.account.AccountDataSource
import com.reddity.app.network.datasource.home.PostsDataSource
import com.reddity.app.network.datasource.subreddit.SubredditDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePostsRepository(
        postsDataSource: PostsDataSource
    ): RedditPostsRepository =
        RedditPostsRepositoryImpl(postsDataSource)

    @Provides
    @Singleton
    fun provideAccountRepository(
        accountDataSource: AccountDataSource
    ): RedditAccountRepository = RedditAccountRepositoryImpl(accountDataSource)

    @Provides
    @Singleton
    fun provideSubredditRepository(
        subredditDataSource: SubredditDataSource
    ): RedditSubredditRepository = RedditSubredditRepositoryImpl(subredditDataSource)
}

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
package com.reddity.app.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.reddity.app.base.IoDispatcher
import com.reddity.app.datastore.datasource.UserDataSource
import com.reddity.app.datastore.datasource.UserDataSourceImpl
import com.reddity.app.datastore.local.LocalRedditUser
import com.reddity.app.datastore.serializer.RedditUserSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesRedditUserDataStore(
        @ApplicationContext context: Context,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
        redditUserSerializer: RedditUserSerializer
    ): DataStore<LocalRedditUser> = DataStoreFactory.create(
        serializer = redditUserSerializer,
        scope = CoroutineScope(coroutineDispatcher + SupervisorJob()),
        produceFile = { context.dataStoreFile("user.pb") }
    )

    @Provides
    @Singleton
    fun provideUserDataSource(
        redditUserDataStore: DataStore<LocalRedditUser>
    ): UserDataSource = UserDataSourceImpl(redditUserDataStore)
}

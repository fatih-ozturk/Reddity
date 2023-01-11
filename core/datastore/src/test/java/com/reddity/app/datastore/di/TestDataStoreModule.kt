/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.reddity.app.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.reddity.app.datastore.local.LocalRedditUser
import com.reddity.app.datastore.serializer.RedditUserSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.junit.rules.TemporaryFolder
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataStoreModule::class]
)
object TestDataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        redditUserSerializer: RedditUserSerializer,
        tmpFolder: TemporaryFolder
    ): DataStore<LocalRedditUser> =
        tmpFolder.testUserPreferencesDataStore(redditUserSerializer)
}

fun TemporaryFolder.testUserPreferencesDataStore(
    redditUserSerializer: RedditUserSerializer = RedditUserSerializer(),
) = DataStoreFactory.create(
    serializer = redditUserSerializer,
) {
    newFile("user_test.pb")
}

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
package com.reddity.app.datastore.datasource

import androidx.datastore.core.DataStore
import com.reddity.app.datastore.local.LocalRedditUser
import com.reddity.app.model.RedditUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException

class UserDataSourceImpl(
    private val redditUserDataStore: DataStore<LocalRedditUser>
) : UserDataSource {

    override fun getUser(): Flow<RedditUser> =
        redditUserDataStore.data.map(LocalRedditUser::asExternal)

    override suspend fun setUser(user: RedditUser) {
        try {
            redditUserDataStore.updateData {
                it.toBuilder()
                    .setUserId(user.id)
                    .setName(user.name)
                    .setAvatar(user.avatarUrl)
                    .setKarma(user.karmaCount)
                    .setCreated(user.createdAt)
                    .setCoins(user.coinCount)
                    .build()
            }
        } catch (exception: IOException) {
            Timber.e(exception)
        }
    }

    override suspend fun clearUser() {
        redditUserDataStore.updateData {
            it.toBuilder().clear().build()
        }
    }
}

fun LocalRedditUser.asExternal(): RedditUser = RedditUser(
    id = userId,
    name = name,
    avatarUrl = avatar,
    karmaCount = karma,
    createdAt = created,
    coinCount = coins
)

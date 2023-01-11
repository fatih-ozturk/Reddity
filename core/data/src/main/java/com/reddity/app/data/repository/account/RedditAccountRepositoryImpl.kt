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
package com.reddity.app.data.repository.account

import com.reddity.app.datastore.datasource.UserDataSource
import com.reddity.app.model.RedditUser
import com.reddity.app.model.Result
import com.reddity.app.network.datasource.account.AccountDataSource
import com.reddity.app.network.model.asExternalModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RedditAccountRepositoryImpl @Inject constructor(
    private val accountDataSource: AccountDataSource,
    private val userDataSource: UserDataSource
) : RedditAccountRepository {
    override suspend fun syncAccount(): Result<Unit> {
        return try {
            val account = accountDataSource.getMe()

            userDataSource.setUser(account.asExternalModel())
            Result.Success(Unit)
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    override fun getAccount(): Flow<RedditUser> = userDataSource.getUser()
}

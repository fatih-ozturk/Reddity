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
package com.reddity.app.network.utils

import com.reddity.app.auth.AuthManager
import com.reddity.app.auth.ReddityAuthManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class ReddityAuthenticator @Inject constructor(
    private val reddityAuthManager: ReddityAuthManager,
    private val authManager: AuthManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? = synchronized(this) {
        runBlocking {
            val currentRetry = responseCount(response)
            if (currentRetry > RETRY_COUNT) {
                authManager.clearAuthState()
                return@runBlocking null
            }

            val originalRequest = response.request
            val requestBuilder = originalRequest.newBuilder()

            reddityAuthManager.refreshAccessToken()

            val authState = authManager.getCurrentAuthState()

            Timber.e("Refresh access token %s", authState.accessToken)
            Timber.e("Current retry %s", currentRetry)

            requestBuilder.header(
                "Authorization",
                "Bearer " + authState.accessToken
            )
            return@runBlocking requestBuilder.build()
        }
    }

    private fun responseCount(response: Response?): Int {
        var result = 1
        var tempResponse = response
        while ((tempResponse?.priorResponse.also { tempResponse = it }) != null) {
            result++
        }
        return result
    }

    companion object {
        private const val RETRY_COUNT = 3
    }
}

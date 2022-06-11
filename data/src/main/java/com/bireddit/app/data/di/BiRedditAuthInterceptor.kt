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
package com.bireddit.app.data.di

import com.bireddit.app.auth.AuthManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class BiRedditAuthInterceptor @Inject constructor(
    private val authManager: AuthManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val oauthBaseUrl = "https://oauth.reddit.com/"
        val baseUrl = "https://reddit.com/"
        if (!authManager.authState.value.isAuthorized) {
            val builder = originalRequest.newBuilder().url(baseUrl).build()
            return chain.proceed(builder)
        }

        val requestBuilder = originalRequest.newBuilder()
            .header("Authorization", "Bearer " + authManager.authState.value.accessToken.toString())
            .url(oauthBaseUrl)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

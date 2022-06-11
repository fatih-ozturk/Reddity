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

    private val url: String
        get() = if (authManager.authState.value.isAuthorized) oauthBaseUrl else baseUrl

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        val urlBuilder = originalRequest.url
            .newBuilder()
            .scheme(originalRequest.url.scheme)
            .host(url)
            .build()

        if (authManager.authState.value.isAuthorized) {
            requestBuilder.header(
                "Authorization",
                "Bearer " + authManager.authState.value.accessToken.toString()
            )
        }

        requestBuilder.url(urlBuilder)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        const val oauthBaseUrl = "oauth.reddit.com"
        const val baseUrl = "reddit.com"
    }
}

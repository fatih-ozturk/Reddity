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
package com.reddity.app.auth

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ClientAuthentication
import timber.log.Timber
import javax.inject.Inject

internal class ReddityAuthManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authManager: AuthManager,
    private val clientAuth: ClientAuthentication,
    private val requestProvider: AuthorizationRequest,
) : ReddityAuthManager {
    private val authService = AuthorizationService(context)

    override fun buildLoginIntent(): Intent =
        authService.getAuthorizationRequestIntent(requestProvider)

    override fun onLoginResult(authorizationResult: LoginRedditContract.AuthorizationResult) {
        val (response, error) = authorizationResult
        when {
            response != null -> {
                authService.performTokenRequest(
                    response.createTokenExchangeRequest(), clientAuth
                ) { token, ex ->
                    val state = authManager.currentAuthState.apply {
                        update(response, ex)
                        update(token, ex)
                    }
                    Timber.e("accessToken = %s", state.accessToken.toString())
                    authManager.onNewAuthState(state)
                }
            }

            error != null -> {
                Timber.e(error.toString())
            }
        }
    }

    override fun refreshAccessToken() {
        try {
            authService.performTokenRequest(
                authManager.currentAuthState.createTokenRefreshRequest(), clientAuth
            ) { token, ex ->
                val state = authManager.currentAuthState.apply {
                    update(token, ex)
                }
                Timber.e("refreshAccessToken = %s", state.accessToken.toString())
                authManager.onNewAuthState(state)
            }
        } catch (exception: IllegalStateException) {
            authManager.clearAuth()
        }
    }
}

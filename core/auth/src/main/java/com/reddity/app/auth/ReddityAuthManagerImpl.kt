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
import dagger.Lazy
import dagger.hilt.android.qualifiers.ApplicationContext
import it.czerwinski.android.hilt.annotations.Bound
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ClientAuthentication
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Bound
@Singleton
internal class ReddityAuthManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authPersistManager: AuthPersistManager,
    private val clientAuth: Lazy<ClientAuthentication>,
    private val requestProvider: Lazy<AuthorizationRequest>,
) : ReddityAuthManager {
    private val authService = AuthorizationService(context)

    override fun buildLoginIntent(): Intent =
        authService.getAuthorizationRequestIntent(requestProvider.get())

    override fun onLoginResult(authorizationResult: LoginRedditContract.AuthorizationResult) {
        val (response, error) = authorizationResult
        when {
            response != null -> {
                authService.performTokenRequest(
                    response.createTokenExchangeRequest(),
                    clientAuth.get()
                ) { token, ex ->
                    val state = AuthState().apply {
                        update(token, ex)
                    }
                    Timber.e(state.accessToken.toString())
                    authPersistManager.onNewAuthState(state)
                }
            }
            error != null -> {
                Timber.e(error.toString())
            }
        }
    }

    override fun refreshAccessToken() {
        authService.performTokenRequest(
            authPersistManager.currentAuthState.createTokenRefreshRequest(),
            clientAuth.get()
        ) { token, ex ->
            val state = AuthState().apply {
                update(token, ex)
            }
            Timber.e(state.accessToken.toString())
            authPersistManager.onNewAuthState(state)
        }
    }
}

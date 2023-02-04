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
import android.content.SharedPreferences
import androidx.core.content.edit
import com.reddity.app.base.restartApp
import dagger.hilt.android.qualifiers.ApplicationContext
import net.openid.appauth.AuthState
import net.openid.appauth.TokenResponse
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(
    @Named("auth") private val authPrefs: SharedPreferences,
    @ApplicationContext private val context: Context
) {

    val state: AuthState
        get() {
            val stateJson = authPrefs.getString(PreferenceAuthKey, null)
            return when {
                stateJson != null -> AuthState.jsonDeserialize(stateJson)
                else -> AuthState()
            }
        }

    fun saveAuthState(state: AuthState) {
        authPrefs.edit(commit = true) {
            putString(PreferenceAuthKey, state.jsonSerializeString())
        }
        if (state.isAuthorized != state.isAuthorized) {
            restartApp()
        }
    }

    fun clearAuthState() {
        authPrefs.edit(commit = true) {
            remove(PreferenceAuthKey)
        }
        restartApp()
    }

    private fun restartApp() {
        context.restartApp()
    }

    fun expireAccessToken() {
        val expiredTokenResponse =
            TokenResponse.Builder(state.createTokenRefreshRequest())
                .setRefreshToken(state.refreshToken)
                .setAccessToken("x")
                .setIdToken(state.lastTokenResponse?.idToken)
                .setScope(state.scope)
                .setTokenType(state.lastTokenResponse?.tokenType)
                .build()

        val expiredAuthState = state.apply {
            update(expiredTokenResponse, null)
        }
        saveAuthState(expiredAuthState)
    }

    companion object {
        private const val PreferenceAuthKey = "authState"
    }
}

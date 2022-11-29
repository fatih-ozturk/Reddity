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

import android.content.SharedPreferences
import androidx.core.content.edit
import com.reddity.app.base.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.openid.appauth.AuthState
import net.openid.appauth.TokenResponse
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@OptIn(DelicateCoroutinesApi::class)
@Singleton
class AuthPersistManager @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @Named("auth") private val authPrefs: SharedPreferences
) {

    val currentAuthState: AuthState
        get() {
            val stateJson = authPrefs.getString(PreferenceAuthKey, null)
            return when {
                stateJson != null -> AuthState.jsonDeserialize(stateJson)
                else -> AuthState()
            }
        }

    fun onNewAuthState(newState: AuthState) {
        GlobalScope.launch(mainDispatcher) {
            persistAuthState(newState)
        }
    }

    private fun persistAuthState(state: AuthState) {
        authPrefs.edit(commit = true) {
            putString(PreferenceAuthKey, state.jsonSerializeString())
        }
    }

    private fun clearPersistedAuthState() {
        authPrefs.edit(commit = true) {
            remove(PreferenceAuthKey)
        }
    }

    fun clearAuth() {
        clearPersistedAuthState()
    }

    fun expireAccessToken() {
        val expiredTokenResponse =
            TokenResponse.Builder(currentAuthState.createTokenRefreshRequest())
                .setRefreshToken(currentAuthState.refreshToken)
                .setAccessToken("x")
                .setIdToken(currentAuthState.lastTokenResponse?.idToken)
                .setScope(currentAuthState.scope)
                .setTokenType(currentAuthState.lastTokenResponse?.tokenType)
                .build()

        val expiredAuthState = currentAuthState.apply {
            update(expiredTokenResponse, null)
        }
        onNewAuthState(expiredAuthState)
    }

    companion object {
        private const val PreferenceAuthKey = "authState"
    }
}

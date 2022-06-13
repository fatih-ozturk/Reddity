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
package com.bireddit.app.auth

import android.content.SharedPreferences
import androidx.core.content.edit
import com.bireddit.app.base.IoDispatcher
import com.bireddit.app.base.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthState
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@OptIn(DelicateCoroutinesApi::class)
@Singleton
class AuthManager @Inject constructor(
    @IoDispatcher private val IoDispatchers: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @Named("auth") private val authPrefs: SharedPreferences
) {
    private val _authState = MutableStateFlow(EmptyAuthState)

    private val _state = MutableSharedFlow<BiRedditAuthState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val state: SharedFlow<BiRedditAuthState> = _state

    init {
        GlobalScope.launch(mainDispatcher) {
            val state = withContext(IoDispatchers) { readAuthState() }
            _authState.value = state
            updateAuthState(state)
        }
    }

    fun onNewAuthState(newState: AuthState) {
        GlobalScope.launch(mainDispatcher) {
            // Update our local state
            _authState.value = newState
            updateAuthState(newState)
        }
        GlobalScope.launch(IoDispatchers) {
            // Persist auth state
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

    private fun readAuthState(): AuthState {
        val stateJson = authPrefs.getString(PreferenceAuthKey, null)
        return when {
            stateJson != null -> AuthState.jsonDeserialize(stateJson)
            else -> AuthState()
        }
    }

    private fun updateAuthState(authState: AuthState) {
        if (authState.isAuthorized) {
            _state.tryEmit(BiRedditAuthState.LOGGED_IN)
        } else {
            _state.tryEmit(BiRedditAuthState.LOGGED_OUT)
        }
    }

    fun isUserAuthorized() = _authState.value.isAuthorized

    fun getUserAuthorizationToken() = _authState.value.accessToken

    fun clearAuth() {
        _authState.value = EmptyAuthState
        clearPersistedAuthState()
        updateAuthState(EmptyAuthState)
    }

    companion object {
        private val EmptyAuthState = AuthState()
        private const val PreferenceAuthKey = "authState"
    }
}

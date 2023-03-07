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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.reddity.app.base.MainDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.openid.appauth.AuthState
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@OptIn(DelicateCoroutinesApi::class)
class AuthManager @Inject constructor(
    @Named("auth") private val dataStore: DataStore<Preferences>,
    @ApplicationContext private val context: Context,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) {

    val state: Flow<AuthState?>
        get() = dataStore.data.map { preferences ->
            val stateJson = preferences[AuthStateKey]
            when {
                stateJson != null -> AuthState.jsonDeserialize(stateJson)
                else -> AuthState()
            }
        }

    suspend fun getCurrentAuthState(): AuthState = state.filterNotNull().first()

    fun saveAuthState(state: AuthState) {
        GlobalScope.launch(mainDispatcher) {
            dataStore.edit { preferences ->
                preferences[AuthStateKey] = state.jsonSerializeString()
            }
        }
    }

    fun clearAuthState() {
        GlobalScope.launch(mainDispatcher) {
            dataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }

    companion object {
        private val AuthStateKey = stringPreferencesKey("authState")
    }
}

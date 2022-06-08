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
package com.bireddit.app.presentation

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.bireddit.app.auth.AuthManager
import com.bireddit.app.auth.RedditAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel @Inject constructor(
    private val redditAuthManager: RedditAuthManager,
    private val authManager: AuthManager,
    @Named("auth") private val authPrefs: SharedPreferences
) : ViewModel(), RedditAuthManager by redditAuthManager

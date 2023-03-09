/*
 * Copyright 2023 Fatih OZTURK
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
package com.reddity.app.communities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reddity.app.domain.usecase.GetAuthStateUseCase
import com.reddity.app.domain.usecase.GetFollowedSubredditsUseCase
import com.reddity.app.model.ReddityAuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CommunitiesViewModel @Inject constructor(
    getAuthStateUseCase: GetAuthStateUseCase,
    private val getFollowedSubredditsUseCase: GetFollowedSubredditsUseCase
) : ViewModel() {

    val uiState: StateFlow<CommunitiesUiState> = getAuthStateUseCase().map { authState ->
        when (authState) {
            ReddityAuthState.LOGGED_IN -> {
                val followedSubreddits = getFollowedSubredditsUseCase()
                CommunitiesUiState.Communities(followedSubreddits)
            }

            ReddityAuthState.LOGGED_OUT -> CommunitiesUiState.UnauthorizedCommunities
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CommunitiesUiState.Loading
    )
}

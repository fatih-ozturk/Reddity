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
package com.reddity.app.home.tabs.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.reddity.app.auth.ReddityAuthManager
import com.reddity.app.base.IoDispatcher
import com.reddity.app.domain.usecase.ChangePostVoteStatusUseCase
import com.reddity.app.domain.usecase.GetAuthStateUseCase
import com.reddity.app.domain.usecase.GetHomePostsUseCase
import com.reddity.app.model.Post
import com.reddity.app.model.PostVoteStatus
import com.reddity.app.model.ReddityAuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeTabViewModel @Inject constructor(
    private val redditAuthManager: ReddityAuthManager,
    getHomePostsUseCase: GetHomePostsUseCase,
    getAuthStateUseCase: GetAuthStateUseCase,
    private val changePostVoteStatusUseCase: ChangePostVoteStatusUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel(), ReddityAuthManager by redditAuthManager {

    val feed: Flow<PagingData<Post>> =
        getHomePostsUseCase().cachedIn(viewModelScope).flowOn(dispatcher)

    val uiState: StateFlow<HomeTabUiState> = getAuthStateUseCase().map { authState ->
        when (authState) {
            ReddityAuthState.LOGGED_IN -> HomeTabUiState.Home
            ReddityAuthState.LOGGED_OUT -> HomeTabUiState.UnauthorizedHome
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeTabUiState.Loading
    )

    @Suppress("UNUSED_PARAMETER")
    fun onVoteClicked(postId: String, vote: PostVoteStatus) = viewModelScope.launch {
        // TODO
    }
}

sealed interface HomeTabUiState {
    object Loading : HomeTabUiState
    object Home : HomeTabUiState
    object UnauthorizedHome : HomeTabUiState
}

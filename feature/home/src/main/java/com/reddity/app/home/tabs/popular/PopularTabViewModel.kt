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
package com.reddity.app.home.tabs.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.reddity.app.base.IoDispatcher
import com.reddity.app.domain.usecase.ChangePostVoteStatusUseCase
import com.reddity.app.domain.usecase.GetAuthStateUseCase
import com.reddity.app.domain.usecase.GetPopularPostsUseCase
import com.reddity.app.model.Post
import com.reddity.app.model.PostVoteStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularTabViewModel @Inject constructor(
    getPopularPostsUseCase: GetPopularPostsUseCase,
    private val changePostVoteStatusUseCase: ChangePostVoteStatusUseCase,
    getAuthStateUseCase: GetAuthStateUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _events: Channel<PopularTabEvent> = Channel(Channel.UNLIMITED)
    val events: Flow<PopularTabEvent> = _events.receiveAsFlow()

    val feed: Flow<PagingData<Post>> =
        getPopularPostsUseCase().cachedIn(viewModelScope).flowOn(dispatcher)

    fun onVoteClicked(
        postId: String,
        vote: PostVoteStatus
    ) = viewModelScope.launch {
        // TODO
    }
}

sealed interface PopularTabEvent {
    object LoginRequired : PopularTabEvent
}

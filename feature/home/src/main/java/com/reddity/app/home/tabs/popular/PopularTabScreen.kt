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
package com.reddity.app.home.tabs.popular

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.reddity.app.home.home.currentVideoPostItem
import com.reddity.app.home.listing.ListingItemView
import com.reddity.app.model.Post
import com.reddity.app.ui.widget.FeedLoadingIcon
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PopularTabScreen(
    viewModel: PopularTabViewModel = hiltViewModel(),
    onLoginRequired: () -> Unit = {},
    listState: LazyListState
) {
    val context = LocalContext.current
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    var playingVideoItem by remember { mutableStateOf<Post?>(null) }

    val items = viewModel.feed.collectAsLazyPagingItems()
    val isRefreshing by remember { derivedStateOf { items.loadState.refresh is LoadState.Loading } }
    val state = rememberPullRefreshState(
        refreshing = items.loadState.refresh is LoadState.Loading,
        onRefresh = { items.refresh() }
    )

    LaunchedEffect(Unit) {
        snapshotFlow {
            listState.currentVideoPostItem(items)
        }.distinctUntilChanged().collect { videoItem ->
            if (videoItem?.videoUrl != null) {
                playingVideoItem = videoItem
            }
        }
    }

    DisposableEffect(exoPlayer) {
        val observer = LifecycleEventObserver { _, event ->
            if (playingVideoItem?.videoUrl == null) return@LifecycleEventObserver

            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.pause()
                }

                Lifecycle.Event.ON_RESUME -> {
                    exoPlayer.play()
                }

                Lifecycle.Event.ON_DESTROY -> {
                    exoPlayer.run {
                        stop()
                        release()
                    }
                }

                else -> {}
            }
        }
        val lifecycle = lifecycleOwner.value.lifecycle
        lifecycle.addObserver(observer)

        onDispose {
            exoPlayer.release()
            lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(playingVideoItem) {
        if (playingVideoItem?.videoUrl == null) {
            exoPlayer.pause()
        } else {
            val mediaItem = MediaItem.Builder()
                .setUri(playingVideoItem?.videoUrl)
                .build()
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.repeatMode = Player.REPEAT_MODE_OFF
            exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }

    Box(
        Modifier
            .pullRefresh(state)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState
        ) {
            itemsIndexed(
                items = items,
                key = { _, item ->
                    item.id
                }
            ) { _, item ->
                if (item == null) return@itemsIndexed
                ListingItemView(
                    post = item,
                    onVoteClicked = {
                        viewModel.onVoteClicked(item.id, it)
                    },
                    onLoginRequired = onLoginRequired,
                    authState = uiState.authState,
                    exoPlayer = exoPlayer,
                    isVideoPlaying = item.videoUrl == playingVideoItem?.videoUrl
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(color = Color(237, 239, 247, 255))
                )
            }
        }
        FeedLoadingIcon(isRefreshing, state, Modifier.align(Alignment.TopCenter))
    }
}

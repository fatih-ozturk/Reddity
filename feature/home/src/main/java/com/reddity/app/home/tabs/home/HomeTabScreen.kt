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
package com.reddity.app.home.tabs.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.reddity.app.auth.LoginRedditContract
import com.reddity.app.home.R
import com.reddity.app.home.home.currentVideoPostItem
import com.reddity.app.home.listing.ListingItemView
import com.reddity.app.model.Post
import com.reddity.app.model.PostVoteStatus
import com.reddity.app.model.ReddityAuthState
import com.reddity.app.ui.widget.FeedLoadingIcon
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun HomeTabScreen(
    viewModel: HomeTabViewModel = hiltViewModel(),
    onLoginRequired: () -> Unit = {},
    listState: LazyListState
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        HomeTabUiState.Home -> {
            val items = viewModel.feed.collectAsLazyPagingItems()

            HomePostsScreen(
                items = items,
                onVoteClicked = { id, itemVoteStatus ->
                    viewModel.onVoteClicked(id, itemVoteStatus)
                },
                onLoginRequired = onLoginRequired,
                authState = ReddityAuthState.LOGGED_IN,
                listState = listState
            )
        }
        HomeTabUiState.Loading -> {
            Text(text = "LOADING")
        }
        HomeTabUiState.Login -> {
            val loginLauncher = rememberLauncherForActivityResult(
                contract = viewModel.buildLoginActivityResult()
            ) { result ->
                if (result != null) {
                    viewModel.onLoginResult(result)
                }
            }

            HomeUnauthorizedScreen(loginLauncher)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePostsScreen(
    items: LazyPagingItems<Post>,
    onVoteClicked: (postId: String, vote: PostVoteStatus) -> Unit,
    onLoginRequired: () -> Unit = {},
    authState: ReddityAuthState,
    listState: LazyListState
) {
    val context = LocalContext.current
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    val isRefreshing by remember { derivedStateOf { items.loadState.refresh is LoadState.Loading } }
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    var playingVideoItem by remember { mutableStateOf<Post?>(null) }

    val state = rememberPullRefreshState(
        refreshing = items.loadState.refresh is LoadState.Loading,
        onRefresh = {
            items.refresh()
        }
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
            itemsIndexed(items = items, key = { _, item ->
                item.id
            }) { index, item ->
                if (item == null) return@itemsIndexed
                ListingItemView(
                    post = item,
                    onVoteClicked = {
                        onVoteClicked.invoke(item.id, it)
                    },
                    onLoginRequired = onLoginRequired,
                    authState = authState,
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

@Composable
fun HomeUnauthorizedScreen(
    loginLauncher: ManagedActivityResultLauncher<Unit, LoginRedditContract.AuthorizationResult?>
) {
    val currentActivity: Context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 42.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.tutorial_post),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Welcome!",
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        modifier = Modifier.padding(top = 2.dp, end = 8.dp),
                        text = "There`s a Reddit community for every topic imaginable",
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.tutorial_vote),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Vote",
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        modifier = Modifier.padding(top = 2.dp, end = 8.dp),
                        text = "on posts and help communities lift the best content to the top!",
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.tutorial_discuss),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Join",
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        modifier = Modifier.padding(top = 2.dp, end = 8.dp),
                        text = "communities to fill this home feed with fresh posts",
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }

        Row(
            modifier = Modifier.padding(bottom = 4.dp, start = 16.dp, end = 16.dp)
        ) {
            Button(
                onClick = {
                    loginLauncher.launch()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                ),
                shape = RoundedCornerShape(50)
            ) {
                Text(text = "Log In")
            }
            Button(
                onClick = {
                    val redditRegisterIntent = getRedditRegisterIntent()
                    currentActivity.startActivity(redditRegisterIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                ),
                shape = RoundedCornerShape(50)
            ) {
                Text(text = "Sign Up")
            }
        }
    }
}

private fun getRedditRegisterIntent(): Intent = Intent(Intent.ACTION_VIEW).apply {
    data = Uri.parse("https://www.reddit.com/register/")
}

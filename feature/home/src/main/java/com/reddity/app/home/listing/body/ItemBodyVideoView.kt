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
package com.reddity.app.home.listing.body

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView.SHOW_BUFFERING_ALWAYS

@Composable
fun ItemBodyVideoView(
    awardCount: Int,
    awardList: List<String>,
    exoPlayer: ExoPlayer,
    isVideoPlaying: Boolean,
    thumbnailUrl: String?
) {
    ItemBodyAwardsView(awardCount = awardCount, awardList = awardList)
    if (isVideoPlaying) {
        ReddityVideoView(exoPlayer = exoPlayer)
    } else {
        ReddityVideoThumbnail(thumbnailUrl)
    }
}

@Composable
fun ReddityVideoView(
    modifier: Modifier = Modifier,
    exoPlayer: Player
) {
    val context = LocalContext.current

    AndroidView(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        factory = {
            StyledPlayerView(context).apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                player = exoPlayer
                resizeMode = RESIZE_MODE_ZOOM
                setShowBuffering(SHOW_BUFFERING_ALWAYS)
                controllerShowTimeoutMs = 1000
                setShowPreviousButton(false)
                setShowNextButton(false)
                setShowFastForwardButton(false)
                setShowRewindButton(false)
                setShowSubtitleButton(false)
            }
        }
    )
}

@Composable
fun ReddityVideoThumbnail(
    thumbnailUrl: String?
) {
    AsyncImage(
        model = thumbnailUrl,
        contentDescription = "Video Thumbnail",
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        contentScale = ContentScale.FillWidth
    )
}

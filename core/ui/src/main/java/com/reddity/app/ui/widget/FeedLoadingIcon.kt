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
package com.reddity.app.ui.widget

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefreshIndicatorTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.reddity.app.ui.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedLoadingIcon(
    refreshing: Boolean,
    state: PullRefreshState,
    modifier: Modifier = Modifier
) {
    val isPLaying by remember(refreshing, state) {
        derivedStateOf { refreshing || state.progress > 0.3f }
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.feed_loading))
    Surface(
        modifier = modifier
            .size(40.dp)
            .offset(y = (-4).dp) // https://issuetracker.google.com/issues/110463864
            .pullRefreshIndicatorTransform(state),
        shape = CircleShape,
        elevation = 6.dp,
    ) {
        LottieAnimation(
            modifier = Modifier.padding(4.dp),
            composition = composition,
            isPlaying = isPLaying,
            iterations = LottieConstants.IterateForever
        )
    }
}

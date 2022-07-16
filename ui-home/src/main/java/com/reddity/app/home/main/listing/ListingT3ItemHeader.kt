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
package com.reddity.app.home.main.listing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.reddity.app.home.R

@Composable
fun ListingT3ItemHeader() {
    Row(
        modifier = Modifier
            .height(48.dp)
            .padding(start = 16.dp, end = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(id = R.drawable.icon_profile),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            alignment = Alignment.CenterEnd,
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = "r/AskReddit",
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "Posted by u/ReverseSwinging • 16h",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground
            )
        }
        Image(
            painterResource(id = R.drawable.icon_overflow_vertical),
            contentDescription = "Post Action Button",
            modifier = Modifier.size(16.dp),
            alignment = Alignment.CenterEnd,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground)
        )
    }
}

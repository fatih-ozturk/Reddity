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
package com.reddity.app.home.listing.item.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.reddity.app.model.Post
import com.reddity.app.ui.commons.LocalReddityTextCreator

@Composable
fun ListingItemHeader(post: Post) {
    val textCreator = LocalReddityTextCreator.current

    Row(
        modifier = Modifier
            .height(48.dp)
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = post.subredditIconUrl,
            contentDescription = "Subreddit Image",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            alignment = Alignment.CenterEnd,
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = textCreator.postReddit(post.subreddit),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = textCreator.postAuthor(post.author),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Image(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Post Action Button",
            modifier = Modifier.size(24.dp),
            alignment = Alignment.CenterEnd,
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
    }
}

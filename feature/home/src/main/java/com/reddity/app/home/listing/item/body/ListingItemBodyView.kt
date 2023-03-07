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
package com.reddity.app.home.listing.item.body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.reddity.app.home.listing.item.body.types.ItemBodyImageView
import com.reddity.app.home.listing.item.body.types.ItemBodySelfView
import com.reddity.app.model.Post
import com.reddity.app.model.PostType

@Composable
fun ListingItemBodyView(post: Post) {
    when (post.postType) {
        PostType.IMAGE -> {
            ItemBodyImageView(
                title = post.title,
                image = post.image
            )
        }

        PostType.SELF -> {
            ItemBodySelfView(
                title = post.title,
                content = post.content
            )
        }

        else -> {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .height(100.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = "\uD83D\uDEE0️ Video and Link are not implemented yet \uD83D\uDEE0️"
                )
            }
        }
    }
}

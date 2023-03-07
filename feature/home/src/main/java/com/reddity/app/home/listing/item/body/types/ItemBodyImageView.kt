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
package com.reddity.app.home.listing.item.body.types

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ItemBodyImageView(title: String, image: String?) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    if (image != null) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 16.dp),
            contentScale = ContentScale.Crop,
            model = image,
            contentDescription = null
        )
    }
}

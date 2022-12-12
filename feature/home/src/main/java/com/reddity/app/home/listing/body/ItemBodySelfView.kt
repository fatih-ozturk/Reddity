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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ItemBodySelfView(
    title: String,
    content: String?,
    awardList: List<String>,
    awardCount: Int
) {
    ItemBodyAwardsView(awardCount = awardCount, awardList = awardList)
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, top = 8.dp),
        text = title,
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.onSurface
    )
    if (!content.isNullOrEmpty()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 8.dp),
            text = content,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

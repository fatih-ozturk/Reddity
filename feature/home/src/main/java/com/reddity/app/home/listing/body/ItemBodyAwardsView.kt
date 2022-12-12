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

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.reddity.app.ui.commons.LocalReddityTextCreator

@Composable
fun ItemBodyAwardsView(
    awardList: List<String>,
    awardCount: Int
) {
    if (awardList.isEmpty()) return
    val textCreator = LocalReddityTextCreator.current
    LazyRow(modifier = Modifier.padding(start = 16.dp), content = {
        awardList.take(4).forEach { awardImageUrl ->
            item {
                AsyncImage(
                    model = awardImageUrl,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .width(16.dp)
                        .height(16.dp)
                        .clip(CircleShape),
                    contentDescription = "Award"
                )
            }
        }
        item {
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = textCreator.awardCount(awardCount),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground
            )
        }
    })
}

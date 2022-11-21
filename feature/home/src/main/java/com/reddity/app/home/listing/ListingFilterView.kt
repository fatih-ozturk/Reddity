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
package com.reddity.app.home.listing

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reddity.app.home.R

@Composable
fun ListingFilterView(
    onFilterViewClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {
                        onFilterViewClicked.invoke()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = R.drawable.icon_best),
                    contentDescription = "Filter Icon",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(16.dp),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "BEST POSTS",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h5,
                    fontSize = 11.sp
                )
                Image(
                    painterResource(id = R.drawable.icon_caret_down),
                    contentDescription = "Caret Down",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(16.dp),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .clickable {},
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(id = R.drawable.icon_view_card),
                    contentDescription = "Caret Down",
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(16.dp),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                )
            }
        }
    }
}

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
package com.reddity.app.home.listing.footer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.reddity.app.home.R
import com.reddity.app.ui.commons.LocalReddityTextCreator

@Composable
fun ItemCommentView(
    onCommentClicked: () -> Unit = {},
    commentCount: Int
) {
    val textCreator = LocalReddityTextCreator.current

    Row(modifier = Modifier.clickable(onClick = onCommentClicked)) {
        Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.icon_comment),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            contentDescription = "Comment"
        )
        Text(
            text = textCreator.postFormattedCountText(commentCount),
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = MaterialTheme.colors.onBackground
        )
    }
}

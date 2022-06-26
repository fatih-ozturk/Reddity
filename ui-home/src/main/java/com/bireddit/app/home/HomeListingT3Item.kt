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
package com.bireddit.app.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bireddit.app.composeui.theme.BiRedditTheme

@Composable
fun HomeListingT3Item() {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
        ) {
            HomeListingT3ItemHeader()
            HomeListingT3ItemBody()
            HomeListingT3ItemFooter()
        }
    }
}

@Composable
fun HomeListingT3ItemFooter() {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .padding(start = 16.dp, end = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        VoteView()
        CommentView()
        ShareView()
        GiveAwardView()
    }
}

@Composable
fun GiveAwardView() {
    Row {
        IconButton(modifier = Modifier.then(Modifier.size(20.dp)), onClick = { /*TODO*/ }) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.icon_award),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                contentDescription = "Give Award"
            )
        }
    }
}

@Composable
fun ShareView() {
    Row {
        IconButton(modifier = Modifier.then(Modifier.size(20.dp)), onClick = { /*TODO*/ }) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.icon_share),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                contentDescription = "Share"
            )
        }
        Text(
            text = "Share",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun CommentView() {
    Row {
        IconButton(modifier = Modifier.then(Modifier.size(20.dp)), onClick = { /*TODO*/ }) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.icon_comment),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                contentDescription = "Comment"
            )
        }
        Text(
            text = "2.1k",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun VoteView() {
    Row {
        IconButton(modifier = Modifier.then(Modifier.size(20.dp)), onClick = { /*TODO*/ }) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.icon_upvote),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                contentDescription = "Upvote"
            )
        }
        Text(
            text = "9.8k",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = MaterialTheme.colors.onBackground
        )
        IconButton(modifier = Modifier.then(Modifier.size(20.dp)), onClick = { /*TODO*/ }) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.icon_downvote),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                contentDescription = "Downvote"
            )
        }
    }
}

@Composable
fun HomeListingT3ItemBody() {
    LazyRow(modifier = Modifier.padding(start = 16.dp), content = {
        item {
            Image(
                painterResource(id = R.drawable.icon_profile),
                modifier = Modifier
                    .padding(end = 4.dp)
                    .width(16.dp)
                    .height(16.dp)
                    .clip(CircleShape),
                contentDescription = "Award"
            )
        }
        item {
            Image(
                painterResource(id = R.drawable.icon_profile),
                modifier = Modifier
                    .padding(end = 4.dp)
                    .width(16.dp)
                    .height(16.dp)
                    .clip(CircleShape),
                contentDescription = "Award"
            )
        }
        item {
            Text(
                modifier = Modifier,
                text = "12 Awards",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground
            )
        }
    })
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, top = 8.dp),
        text = Mocks.title,
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colors.onSurface
    )
    val contextText by remember { mutableStateOf(Mocks.contentText) }
    if (contextText.isNotBlank()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 8.dp),
            text = contextText,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    } else {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            painter = painterResource(id = R.drawable.sample),
            contentDescription = "Content image"
        )
    }
    // TODO Video
}

@Preview(showBackground = true)
@Composable
fun HomeListingT3ItemPreview() {
    BiRedditTheme {
        HomeListingT3Item()
    }
}

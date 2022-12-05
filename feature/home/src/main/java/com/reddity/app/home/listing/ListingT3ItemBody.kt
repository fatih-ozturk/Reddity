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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.reddity.app.model.Post
import com.reddity.app.model.PostType
import com.reddity.app.ui.commons.LocalReddityTextCreator
import com.reddity.app.ui.theme.ReddityTheme

@Composable
fun ListingT3ItemBody(post: Post) {

    when (post.postType) {
        PostType.IMAGE -> {
            ItemBodyImage(
                title = post.title,
                image = post.image,
                awardCount = post.awardsCount,
                awardList = post.awardsIconList
            )
        }

        PostType.LINK -> {
            ItemBodyLink(
                title = post.title,
                image = post.image,
                awardCount = post.awardsCount,
                awardList = post.awardsIconList
            )
        }

        PostType.HOSTED_VIDEO, PostType.RICH_VIDEO -> {
            ReddityVideoPlayer(
                awardCount = post.awardsCount,
                awardList = post.awardsIconList
            )
        }

        PostType.SELF -> {
            ItemBodySelf(
                title = post.title,
                content = post.content,
                awardCount = post.awardsCount,
                awardList = post.awardsIconList
            )
        }
    }
}

@Composable
fun ReddityVideoPlayer(
    awardCount: Int,
    awardList: List<String>
) {
    ListingT3ItemBodyAwards(awardCount = awardCount, awardList = awardList)
    // TODO auto play video player
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 8.dp)
            .background(color = Color.Black)
    )
}

@Composable
fun ItemBodyImage(title: String, image: String?, awardCount: Int, awardList: List<String>) {
    Row {
        Column(modifier = Modifier.weight(1f)) {
            ListingT3ItemBodyAwards(awardCount = awardCount, awardList = awardList)
            if (title.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 8.dp, top = 8.dp),
                    text = title,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onSurface,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (image != null) {
                AsyncImage(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentScale = ContentScale.Crop,
                    model = image,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun ItemBodyLink(title: String, image: String?, awardCount: Int, awardList: List<String>) {
    Row {
        Column(modifier = Modifier.weight(1f)) {
            ListingT3ItemBodyAwards(awardCount = awardCount, awardList = awardList)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, top = 8.dp),
                text = title,
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onSurface,
                overflow = TextOverflow.Ellipsis,
            )
        }
        if (image != null) {
            AsyncImage(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(width = 100.dp, height = 75.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop,
                model = image,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemBodyLinkPreview() {
    ReddityTheme {
        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {
            ItemBodyLink("post.title", "post.image", 3, listOf())
        }
    }
}

@Composable
private fun ItemBodySelf(
    title: String,
    content: String?,
    awardList: List<String>,
    awardCount: Int
) {
    ListingT3ItemBodyAwards(awardCount = awardCount, awardList = awardList)
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

@Composable
fun ListingT3ItemBodyAwards(
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

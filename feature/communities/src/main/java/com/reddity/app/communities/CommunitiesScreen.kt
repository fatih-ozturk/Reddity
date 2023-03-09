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
package com.reddity.app.communities

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.reddity.app.model.FollowedSubreddits
import com.reddity.app.ui.component.NavigationDrawerDivider
import com.reddity.app.ui.component.NavigationDrawerTitle
import com.reddity.app.ui.theme.ReddityTheme

@Composable
fun CommunitiesScreen(
    viewModel: CommunitiesViewModel = hiltViewModel(),
    onAllCommunityClick: () -> Unit = { },
    onCommunityClick: (String) -> Unit = { },
    onLoginClick: () -> Unit = { },
    onCreateCommunityClick: () -> Unit = { }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CommunitiesContent(
        uiState = uiState,
        onAllCommunityClick = onAllCommunityClick,
        onCreateCommunityClick = onCreateCommunityClick,
        onLoginClick = onLoginClick,
        onCommunityClick = onCommunityClick
    )
}

@Composable
fun CommunitiesContent(
    uiState: CommunitiesUiState,
    onAllCommunityClick: () -> Unit = {},
    onCreateCommunityClick: () -> Unit = {},
    onCommunityClick: (String) -> Unit = {},
    onLoginClick: () -> Unit = { }
) {
    when (uiState) {
        CommunitiesUiState.Loading -> Unit
        CommunitiesUiState.UnauthorizedCommunities -> {
            UnauthorizedCommunitiesView(
                onAllCommunityClick = onAllCommunityClick,
                onLoginClick = onLoginClick
            )
        }

        is CommunitiesUiState.Communities -> {
            CommunitiesView(
                followedSubreddits = uiState.list,
                onCommunityClick = onCommunityClick,
                onAllCommunityClick = onAllCommunityClick,
                onCreateCommunityClick = onCreateCommunityClick
            )
        }
    }
}

@Composable
fun CommunitiesView(
    followedSubreddits: List<FollowedSubreddits>,
    onCommunityClick: (communityId: String) -> Unit = { },
    onCreateCommunityClick: () -> Unit = { },
    onAllCommunityClick: () -> Unit = { }
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp)
    ) {
        item {
            NavigationDrawerTitle(title = "Your Communities")
        }
        item {
            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Create a community",
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                selected = false,
                onClick = { onCreateCommunityClick() },
                icon = {
                    Image(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            )
        }
        items(followedSubreddits) { item ->
            NavigationDrawerItem(
                label = {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                selected = false,
                onClick = { onCommunityClick(item.id) },
                icon = {
                    val defaultIcon = rememberVectorPainter(image = Icons.Default.ShowChart)
                    val colorFilter = if (item.icon.isEmpty()) {
                        ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                    } else {
                        null
                    }
                    AsyncImage(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape),
                        model = item.icon,
                        error = defaultIcon,
                        contentDescription = null,
                        colorFilter = colorFilter
                    )
                }
            )
        }
        item {
            NavigationDrawerDivider()
            CommunityAllDrawerItem(onAllCommunityClick = onAllCommunityClick)
        }
    }
}

@Composable
fun UnauthorizedCommunitiesView(
    onAllCommunityClick: () -> Unit = {},
    onLoginClick: () -> Unit = { }
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp)
    ) {
        item {
            CommunityAllDrawerItem(onAllCommunityClick = onAllCommunityClick)
        }
        item {
            NavigationDrawerItem(
                label = {
                    Text(
                        text = "Log in to add your communities",
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                selected = false,
                onClick = { onLoginClick() },
                icon = {
                    Image(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.PersonOutline,
                        contentDescription = "login",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            )
        }
    }
}

@Composable
fun CommunityAllDrawerItem(onAllCommunityClick: () -> Unit) {
    NavigationDrawerItem(
        label = {
            Text(
                text = "All",
                style = MaterialTheme.typography.labelLarge
            )
        },
        selected = false,
        onClick = { onAllCommunityClick() },
        icon = {
            Image(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.ShowChart,
                contentDescription = "communities_all",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
    )
}

@Preview(uiMode = UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = UI_MODE_NIGHT_YES, name = "Dark theme")
@Composable
fun UnauthorizedCommunitiesViewPreview() {
    ReddityTheme {
        ModalDrawerSheet {
            UnauthorizedCommunitiesView()
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = UI_MODE_NIGHT_YES, name = "Dark theme")
@Composable
fun CommunitiesViewPreview() {
    ReddityTheme {
        ModalDrawerSheet {
            CommunitiesView(
                listOf(
                    FollowedSubreddits(
                        id = "1",
                        "r/Jokes",
                        "https://b.thumbs.redditmedia.com/ea6geuS5pIeDjJdtVdbcgfQYX-RwGYwsbHB02tCJmMs.png"
                    ),
                    FollowedSubreddits(
                        id = "2",
                        "r/memes",
                        "https://styles.redditmedia.com/t5_2qjpg/styles/communityIcon_uzvo7sibvc3a1.jpg"
                    )
                )
            )
        }
    }
}

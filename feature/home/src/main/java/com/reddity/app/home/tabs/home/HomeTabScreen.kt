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
package com.reddity.app.home.tabs.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.reddity.app.auth.LoginRedditContract
import com.reddity.app.home.R
import com.reddity.app.home.listing.ReddityPostListingView

@Composable
fun HomeTabScreen(
    viewModel: HomeTabViewModel = hiltViewModel(),
    listState: LazyListState
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        HomeTabUiState.Home -> ReddityPostListingView(
            items = viewModel.feed.collectAsLazyPagingItems(),
            onVoteClicked = viewModel::onVoteClicked,
            listState = listState
        )
        HomeTabUiState.Loading -> Unit
        HomeTabUiState.UnauthorizedHome -> {
            HomeUnauthorizedScreen(
                loginRedditContract = viewModel.buildLoginActivityResult(),
                onLoginResult = viewModel::onLoginResult
            )
        }
    }
}

@Composable
fun HomeUnauthorizedScreen(
    loginRedditContract: LoginRedditContract,
    onLoginResult: (LoginRedditContract.AuthorizationResult) -> Unit
) {
    val loginLauncher = rememberLauncherForActivityResult(
        contract = loginRedditContract
    ) { result ->
        if (result != null) {
            onLoginResult(result)
        }
    }
    val currentActivity: Context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 42.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.tutorial_post),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Welcome!",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        modifier = Modifier.padding(top = 2.dp, end = 8.dp),
                        text = "There`s a Reddit community for every topic imaginable",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.tutorial_vote),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Vote",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        modifier = Modifier.padding(top = 2.dp, end = 8.dp),
                        text = "on posts and help communities lift the best content to the top!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.tutorial_discuss),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Join",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        modifier = Modifier.padding(top = 2.dp, end = 8.dp),
                        text = "communities to fill this home feed with fresh posts",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Row(
            modifier = Modifier.padding(bottom = 4.dp, start = 16.dp, end = 16.dp)
        ) {
            Button(
                onClick = {
                    loginLauncher.launch()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = "Log In")
            }
            Button(
                onClick = {
                    val redditRegisterIntent = getRedditRegisterIntent()
                    currentActivity.startActivity(redditRegisterIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = "Sign Up")
            }
        }
    }
}

private fun getRedditRegisterIntent(): Intent = Intent(Intent.ACTION_VIEW).apply {
    data = Uri.parse("https://www.reddit.com/register/")
}

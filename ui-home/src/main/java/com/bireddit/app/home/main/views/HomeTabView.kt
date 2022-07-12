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
package com.bireddit.app.home.main.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bireddit.app.composeui.theme.BiRedditTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeTabView(
    pagerState: PagerState,
    coroutineScope: CoroutineScope
) {
    val isSelected = pagerState.currentPage == 0
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TabRow(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(0.5f),
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = MaterialTheme.colors.surface,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.pagerTabIndicatorOffset(
                            pagerState = pagerState,
                            tabPositions = tabPositions
                        ),
                        height = 2.dp,
                        color = MaterialTheme.colors.secondary
                    )
                }
            ) {
                Tab(modifier = Modifier, selected = isSelected, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }) {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onSurface
                    )
                }
                Tab(modifier = Modifier, selected = isSelected, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }) {
                    Text(
                        text = "Popular",
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun HomeTabViewPreview() {
    BiRedditTheme {
        HomeTabView(pagerState = rememberPagerState(), coroutineScope = rememberCoroutineScope())
    }
}

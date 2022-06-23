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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bireddit.app.composeui.theme.BiRedditTheme

@Composable
fun HomeTabView() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    TabRow(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        selectedTabIndex = selectedTabIndex,
        backgroundColor = MaterialTheme.colors.surface,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                height = 2.dp,
                color = MaterialTheme.colors.secondary
            )
        },
        tabs = {
            Tab(
                modifier = Modifier,
                selected = true,
                onClick = { selectedTabIndex = 0 }
            ) {
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.onSurface
                )
            }
            Tab(
                modifier = Modifier,
                selected = false,
                onClick = { selectedTabIndex = 1 }
            ) {
                Text(
                    text = "Popular",
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun HomeTabViewPreview() {
    BiRedditTheme {
        HomeTabView()
    }
}

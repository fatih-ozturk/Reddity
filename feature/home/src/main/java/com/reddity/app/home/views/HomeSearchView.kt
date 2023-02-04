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
package com.reddity.app.home.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.reddity.app.home.R
import com.reddity.app.home.home.ToolbarUiState

@Composable
fun HomeSearchView(
    onMenuClicked: (() -> Unit)? = {},
    onProfileClicked: (() -> Unit)? = {},
    toolbarUiState: ToolbarUiState
) {
    var searchText by remember { mutableStateOf("") }
    var onSearchFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    Surface {
        Row(
            Modifier
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = {
                    if (onSearchFocused) {
                        focusManager.clearFocus()
                    } else {
                        onMenuClicked?.invoke()
                    }
                },
                modifier = Modifier.then(Modifier.size(20.dp))
            ) {
                Image(
                    painterResource(
                        id = if (onSearchFocused) R.drawable.icon_back else R.drawable.icon_menu
                    ),
                    contentDescription = "Drawer Menu",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                    modifier = Modifier
                )
            }
            RedditySearchTextField(
                modifier = Modifier.weight(1f),
                searchText,
                onFocusChanged = {
                    onSearchFocused = it.hasFocus
                },
                doOnTextChange = {
                    searchText = it
                },
                onSearchFocused = onSearchFocused
            )
            AnimatedVisibility(visible = !onSearchFocused) {
                AsyncImage(
                    model = toolbarUiState.redditUser?.avatarUrl,
                    placeholder = painterResource(id = R.drawable.icon_user_fill),
                    error = painterResource(id = R.drawable.icon_user_fill),
                    fallback = painterResource(id = R.drawable.icon_user_fill),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(36.dp)
                        .padding(start = 16.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            onProfileClicked?.invoke()
                        },
                    alignment = Alignment.CenterEnd,
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun RedditySearchTextField(
    modifier: Modifier,
    searchText: String,
    doOnTextChange: (String) -> Unit,
    onFocusChanged: (FocusState) -> Unit,
    onSearchFocused: Boolean
) {
    Row(
        modifier = modifier
            .padding(start = 16.dp)
            .height(36.dp)
            .background(MaterialTheme.colors.background, MaterialTheme.shapes.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(id = R.drawable.icon_search),
            contentDescription = "null",
            modifier = Modifier
                .padding(start = 8.dp)
                .size(16.dp),
            alignment = Alignment.CenterStart,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
        )
        Box(modifier = Modifier.weight(1f)) {
            BasicTextField(
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .fillMaxWidth()
                    .onFocusChanged(onFocusChanged),
                value = searchText,
                textStyle = MaterialTheme.typography.h4,
                onValueChange = doOnTextChange,
                keyboardActions = KeyboardActions {
                    this.defaultKeyboardAction(ImeAction.Search)
                },
                singleLine = true,
                cursorBrush = SolidColor(value = MaterialTheme.colors.onBackground)
            )
            if (searchText.isEmpty()) {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 16.dp)
                        .fillMaxWidth()
                )
            }
        }
        if (onSearchFocused) {
            IconButton(onClick = {
                doOnTextChange.invoke("")
            }) {
                Image(
                    painterResource(id = R.drawable.icon_toolbar_close),
                    contentDescription = "null",
                    modifier = Modifier.size(16.dp),
                    alignment = Alignment.CenterEnd,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                )
            }
        }
    }
}

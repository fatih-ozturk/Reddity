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
package com.reddity.app.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reddity.app.ui.theme.ReddityTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val loginLauncher = rememberLauncherForActivityResult(
        contract = viewModel.buildLoginActivityResult()
    ) { result ->
        if (result != null) {
            viewModel.onLoginResult(result)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = "Login your account to continue",
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onSurface
        )
        Text(
            modifier = Modifier.padding(top = 12.dp),
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.onSurface,
            text = "By continuing, you agree to Reddit User Agreement and acknowledge " +
                "that you understand the Privacy Policy"
        )
        LoginButton(
            onLoginClicked = {
                loginLauncher.launch(Unit)
            }
        )
    }
}

@Composable
fun LoginButton(
    onLoginClicked: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                shape = RoundedCornerShape(50.dp)
            )
            .clip(shape = RoundedCornerShape(50.dp))
            .clickable { onLoginClicked() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.padding(12.dp),
                painter = painterResource(id = R.drawable.icon_reddit),
                contentDescription = "Reddit icon"
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 48.dp),
                text = "Continue with Reddit",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    ReddityTheme {
        Surface {
            LoginScreen()
        }
    }
}

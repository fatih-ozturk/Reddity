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
package com.bireddit.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bireddit.app.auth.BiRedditAuthState
import com.bireddit.app.presentation.theme.BiRedditTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BiRedditTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen() {
        Column {
            val viewModel: MainViewModel = hiltViewModel()
            val isLogin by viewModel.getAuthManagerState().collectAsState(null)
            when (isLogin) {
                BiRedditAuthState.LOGGED_IN -> Timber.e("LOGIN")
                BiRedditAuthState.LOGGED_OUT -> Timber.e("LOGOUT")
                else -> {
                    // do nothing while fetching local auth state
                }
            }
            val loginLauncher = rememberLauncherForActivityResult(
                contract = viewModel.buildLoginActivityResult()
            ) { result ->
                if (result != null) {
                    viewModel.onLoginResult(result)
                }
            }
            Button(onClick = {
                loginLauncher.launch(Unit)
            }, content = {
                Text(text = "Login")
            })
            Button(onClick = {
                viewModel.logout()
            }, content = {
                Text(text = "Logout")
            })
            Button(onClick = {
                viewModel.test()
            }, content = {
                Text(text = "request")
            })
        }
    }
}

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

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bireddit.app.auth.RedditAuthManager
import com.bireddit.app.data.api.RedditHomeApi
import com.bireddit.app.data.model.RedditListingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val redditAuthManager: RedditAuthManager,
    private val redditHomeApi: RedditHomeApi
) : ViewModel(), RedditAuthManager by redditAuthManager {
    fun test() {
        redditHomeApi.getHomeHotFeed().enqueue(object : Callback<RedditListingResponse?> {
            override fun onResponse(
                call: Call<RedditListingResponse?>,
                response: Response<RedditListingResponse?>
            ) {
                Log.e("FAT", response.toString())
            }

            override fun onFailure(call: Call<RedditListingResponse?>, t: Throwable) {
                Log.e("FAT ERR", t.toString())
            }
        })
    }
}

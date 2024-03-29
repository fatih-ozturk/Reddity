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
package com.reddity.app.auth

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

class LoginRedditContract internal constructor(
    private val intentBuilder: () -> Intent
) : ActivityResultContract<Unit, LoginRedditContract.AuthorizationResult?>() {

    override fun createIntent(context: Context, input: Unit): Intent = intentBuilder()

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): AuthorizationResult? = intent?.let {
        AuthorizationResult(
            AuthorizationResponse.fromIntent(it),
            AuthorizationException.fromIntent(it)
        )
    }

    data class AuthorizationResult(
        val response: AuthorizationResponse?,
        val exception: AuthorizationException?
    )
}

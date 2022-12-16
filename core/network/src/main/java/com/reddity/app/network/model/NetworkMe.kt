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
package com.reddity.app.network.model

import android.os.Parcelable
import com.reddity.app.model.RedditUser
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class NetworkMe(
    @Json(name = "id") val id: String,
    @Json(name = "name") val fullname: String,
    @Json(name = "coins") val coins: Int,
    @Json(name = "comment_karma") val commentKarma: Int,
    @Json(name = "snoovatar_img") val avatarUrl: String,
    @Json(name = "created") val created: Int,
    @Json(name = "total_karma") val karmaCount: Int,
    @Json(name = "has_mail") val hasMail: Boolean,
    @Json(name = "has_mod_mail") val hasModMail: Boolean,
    @Json(name = "has_verified_email") val hasVerifiedEmail: Boolean,
    @Json(name = "inbox_count") val inboxCount: Int,
    @Json(name = "link_karma") val linkKarma: Int,
    @Json(name = "num_friends") val numFriends: Int,
    @Json(name = "over_18") val over18: Boolean,
    @Json(name = "verified") val verified: Boolean,
) : Parcelable

fun NetworkMe.asExternalModel(): RedditUser = RedditUser(
    id = id,
    name = fullname,
    avatarUrl = avatarUrl,
    karmaCount = karmaCount,
    createdAt = created,
    coinCount = coins
)

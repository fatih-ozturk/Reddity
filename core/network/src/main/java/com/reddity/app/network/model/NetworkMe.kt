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
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class NetworkMe(

    @Json(name = "id")
    val id: String,

    @Json(name = "name")
    val fullname: String,

    @Json(name = "coins")
    val coins: Int,

    @Json(name = "comment_karma")
    val commentKarma: Int,

    @Json(name = "created")
    val created: Long,

    @Json(name = "created_utc")
    val createdUtc: Long,

    @Json(name = "has_mail")
    val hasMail: Boolean,

    @Json(name = "has_mod_mail")
    val hasModMail: Boolean,

    @Json(name = "has_verified_email")
    val hasVerifiedEmail: Boolean,

    @Json(name = "inbox_count")
    val inboxCount: Int,

    @Json(name = "is_employee")
    val isEmployee: Boolean,

    @Json(name = "is_gold")
    val isGold: Boolean,

    @Json(name = "hide_from_robots")
    val isHidingFromRobots: Boolean,

    @Json(name = "is_mod")
    val isMod: Boolean,

    @Json(name = "is_sponsor")
    val isSponsor: Boolean,

    @Json(name = "is_suspended")
    val isSuspended: Boolean,

    @Json(name = "link_karma")
    val linkKarma: Int,

    @Json(name = "num_friends")
    val numFriends: Int,

    @Json(name = "over_18")
    val over18: Boolean,

    @Json(name = "verified")
    val verified: Boolean,

    @Json(name = "features")
    val features: @RawValue Map<String, Any>

) : Parcelable

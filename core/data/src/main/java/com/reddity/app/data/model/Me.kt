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
package com.reddity.app.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * This class represent the current logged in user.
 *
 * @property id This item identifier, e.g. "8xwlg"
 *
 * @property fullname fullname of this class.
 *
 * @property created The unix-time Long representing the creation date of the comment.
 *
 * @property createdUtc The unix-time Long representing the UTC creation date of the comment.
 *
 * @property hasMail Whether the user has unread mail.
 *
 * @property hasModMail Whether the user has unread mod mail.
 *
 * @property commentKarma karma obtained through submitting a comment.
 *
 * @property linkKarma karma obtained through submitting a post (Submission).
 *
 * @property hasVerifiedEmail if the user has a verified email.
 *
 * @property inboxCount Number of unread messages in the inbox.
 *
 * @property isEmployee if the user is an employee of Reddit.
 *
 * @property isGold if the user has a gold status.
 *
 * @property isMod if the user is Mod.
 *
 * @property over18 whether this account is set to be over 18.
 *
 */
@JsonClass(generateAdapter = true)
@Parcelize
data class Me(

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

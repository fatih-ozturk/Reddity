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
import kotlinx.parcelize.Parcelize

@Parcelize
enum class EnvelopeKind(val type: String) : Parcelable {
    COMMENT("t1"),
    ACCOUNT("t2"),
    LINK("t3"),
    MESSAGE("t4"),
    SUBREDDIT("t5"),
    AWARD("t6"),
    Listing("Listing"),
    More("more");

    companion object {
        fun of(type: String): EnvelopeKind? = values().firstOrNull { it.type == type }
    }
}

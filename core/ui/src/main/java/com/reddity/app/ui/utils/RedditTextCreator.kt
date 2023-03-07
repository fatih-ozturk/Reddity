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
package com.reddity.app.ui.utils

import javax.inject.Inject
import kotlin.math.ln
import kotlin.math.pow

class RedditTextCreator @Inject constructor() {
    fun postFormattedCountText(count: Int): String {
        if (count < 1000) return "" + count
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f%c", count / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
    }

    fun postReddit(reddit: String): String {
        return "r/$reddit"
    }

    fun postAuthor(author: String): String {
        return "u/$author"
    }

    fun awardCount(count: Int): String {
        return if (count == 1) "$count Award" else "$count Awards"
    }
}

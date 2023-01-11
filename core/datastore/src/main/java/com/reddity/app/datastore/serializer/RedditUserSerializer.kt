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
package com.reddity.app.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.reddity.app.datastore.local.LocalRedditUser
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

/**
 * Serializer for [LocalRedditUser] defined in local_reddit_user.proto.
 */
class RedditUserSerializer @Inject constructor() : Serializer<LocalRedditUser> {
    override val defaultValue: LocalRedditUser = LocalRedditUser.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): LocalRedditUser {
        try {
            return LocalRedditUser.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: LocalRedditUser, output: OutputStream) = t.writeTo(output)
}

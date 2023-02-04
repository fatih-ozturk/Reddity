package com.reddity.app.datastore.serializer

import androidx.datastore.core.CorruptionException
import com.google.common.truth.Truth.assertThat
import com.reddity.app.datastore.local.LocalRedditUser
import com.reddity.app.datastore.local.localRedditUser
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class RedditUserSerializerTest {
    private val redditUserSerializer = RedditUserSerializer()

    @Test
    fun `default RedditUserPreferences is empty`() {
        val defaultRedditUserPreferences = localRedditUser {}


        assertThat(defaultRedditUserPreferences)
            .isEqualTo(redditUserSerializer.defaultValue)
    }

    @Test
    fun `write and read RedditUserPreferences verify correct value `() = runTest {
        val expectedResult = localRedditUser {
            userId = "userId"
        }

        val outputStream = ByteArrayOutputStream()
        expectedResult.writeTo(outputStream)
        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val actualUserPreferences = redditUserSerializer.readFrom(inputStream)

        assertThat(expectedResult)
            .isEqualTo(actualUserPreferences)
    }

    @Test(expected = CorruptionException::class)
    fun `reading Invalid RedditUserPreferences throws CorruptionException`() = runTest {
        redditUserSerializer.readFrom(ByteArrayInputStream(byteArrayOf(0)))
    }

    @Test
    fun `writing RedditUserPreferences verify correct value`() = runTest {
        val expectedResult = localRedditUser {
            userId = "userId"
        }
        val outputStream = ByteArrayOutputStream()

        redditUserSerializer.writeTo(expectedResult, outputStream)

        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val localRedditUser = LocalRedditUser.parseFrom(inputStream)
        assertThat(expectedResult).isEqualTo(localRedditUser)
    }
}

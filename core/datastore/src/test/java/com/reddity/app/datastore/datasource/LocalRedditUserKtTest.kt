package com.reddity.app.datastore.datasource

import com.google.common.truth.Truth.assertThat
import com.reddity.app.datastore.local.LocalRedditUser
import com.reddity.app.model.RedditUser
import org.junit.Test

class LocalRedditUserKtTest {

    @Test
    fun `LocalRedditUser can map to RedditUser`() {
        val expectedRedditUser = RedditUser(
            "userId",
            "username",
            "avatarUrl",
            0,
            0,
            0
        )
        val localRedditUser = LocalRedditUser.newBuilder()
            .setUserId("userId")
            .setName("username")
            .setAvatar("avatarUrl")
            .setKarma(0)
            .setCreated(0)
            .setCoins(0)
            .build()

        val redditUser = localRedditUser.asExternal()

        assertThat(expectedRedditUser).isEqualTo(redditUser)
    }
}

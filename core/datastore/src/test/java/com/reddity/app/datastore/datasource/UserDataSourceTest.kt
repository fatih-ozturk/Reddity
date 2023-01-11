package com.reddity.app.datastore.datasource

import androidx.datastore.core.DataStore
import com.reddity.app.datastore.di.testUserPreferencesDataStore
import com.reddity.app.datastore.local.LocalRedditUser
import com.reddity.app.testing.MainCoroutineRule
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder

class UserDataSourceTest {

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    private lateinit var userDataSource: UserDataSource
    private lateinit var redditUserDataStore: DataStore<LocalRedditUser>

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        redditUserDataStore = tmpFolder.testUserPreferencesDataStore()
        userDataSource = UserDataSourceImpl(redditUserDataStore)
    }

    /*@Test
    fun `update reddit user then verify reddit user flow`() = runTest {
        val localRedditUser = localRedditUser {
            userId = "userId"
        }
        userDataSource.getUser().test {
            redditUserDataStore.updateData {
                localRedditUser
            }

            assertThat(localRedditUser.userId).isEqualTo(awaitItem().id)
        }
    }*/


}

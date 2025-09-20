package com.example.notesapp.unitaries.data.local_data_source.databases

import com.example.notesapp.concerns.RoomDatabaseFactory.getInstance
import com.example.notesapp.data.local_data_source.databases.UserDatabase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserDatabaseTest {
    private lateinit var userDatabase: UserDatabase

    @Before
    fun beforeEach() {
        userDatabase = getInstance(UserDatabase::class.java)
    }

    @Test
    fun testIfMethodGetDataAccessObjectReturnsUserDataAccessObjectImplementation() {
        runTest {
            val userDataAccessObject = userDatabase.getDataAccessObject()

            val usersFromDatabase = userDataAccessObject.getUsers()

            assertTrue(usersFromDatabase.isEmpty())
        }
    }
}
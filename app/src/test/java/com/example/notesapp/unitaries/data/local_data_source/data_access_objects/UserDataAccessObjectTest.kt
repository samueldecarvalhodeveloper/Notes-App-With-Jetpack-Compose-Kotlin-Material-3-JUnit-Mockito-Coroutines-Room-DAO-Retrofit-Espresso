package com.example.notesapp.unitaries.data.local_data_source.data_access_objects

import com.example.notesapp.concerns.RoomDatabaseFactory
import com.example.notesapp.constants.data.UserDataConstants.USER_ENTITY_OBJECT
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject
import com.example.notesapp.data.local_data_source.databases.UserDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UserDataAccessObjectTest {
    private lateinit var userDataAccessObject: UserDataAccessObject

    @Before
    fun beforeEach() {
        val userDatabase = RoomDatabaseFactory.getInstance(UserDatabase::class.java)

        userDataAccessObject = userDatabase.getDataAccessObject()
    }

    @Test
    fun testIfMethodGetUsersReturnsAllUsersFromDatabase() {
        runTest {
            val usersFromDatabase = userDataAccessObject.getUsers()

            assertTrue(usersFromDatabase.isEmpty())
        }
    }

    @Test
    fun testIfMethodCreateUserReturnsCreatesUserOnDatabase() {
        runTest {
            userDataAccessObject.createUser(USER_ENTITY_OBJECT)

            val usersFromDatabase = userDataAccessObject.getUsers()

            assertEquals(USER_ID, usersFromDatabase.first().id)
            assertEquals(USER_USERNAME, usersFromDatabase.first().username)
        }
    }
}
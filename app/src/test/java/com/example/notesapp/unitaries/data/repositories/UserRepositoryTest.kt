package com.example.notesapp.unitaries.data.repositories

import com.example.notesapp.concerns.RoomDatabaseFactory.getInstance
import com.example.notesapp.concerns.WebServerMock.WEB_SERVER_MOCK_URL
import com.example.notesapp.concerns.WebServerMock.enqueueResponse
import com.example.notesapp.concerns.WebServerMock.startServer
import com.example.notesapp.concerns.WebServerMock.stopServer
import com.example.notesapp.constants.data.UserDataConstants.USER_ENTITY_OBJECT
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.constants.data.UserDataConstants.USER_JSON
import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject
import com.example.notesapp.data.local_data_source.databases.UserDatabase
import com.example.notesapp.data.remote_data_source.services.UserService
import com.example.notesapp.data.repositories.UserRepository
import kotlinx.coroutines.test.runTest
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.net.HttpURLConnection

@RunWith(RobolectricTestRunner::class)
class UserRepositoryTest {
    private lateinit var userDataAccessObject: UserDataAccessObject
    private lateinit var userRepository: UserRepository

    @Before
    fun beforeEach() {
        val userDatabase = getInstance(UserDatabase::class.java)

        userDataAccessObject = userDatabase.getDataAccessObject()
        val userGateway = UserService.getInstance(WEB_SERVER_MOCK_URL)

        userRepository = UserRepository(userGateway, userDataAccessObject)
    }

    @Test
    fun testIfMethodGetUserReturnsStoredUserOnDatabase() {
        runTest {
            userDataAccessObject.createUser(USER_ENTITY_OBJECT)

            val userFromDatabase = userRepository.getUser()

            assertEquals(USER_ID, userFromDatabase.id)
            assertEquals(USER_USERNAME, userFromDatabase.username)
        }
    }

    @Test
    fun testIfMethodGetCreatedUserCreatesUserOnServerAndOnDatabase() {
        enqueueResponse(USER_JSON, HttpURLConnection.HTTP_CREATED)

        runTest {
            val createdUserOnService = userRepository.getCreatedUser(USER_USERNAME)

            assertEquals(USER_ID, createdUserOnService.id)
            assertEquals(USER_USERNAME, createdUserOnService.username)
        }
    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeAll() {
            startServer()
        }

        @JvmStatic
        @AfterClass
        fun afterAll() {
            stopServer()
        }
    }
}
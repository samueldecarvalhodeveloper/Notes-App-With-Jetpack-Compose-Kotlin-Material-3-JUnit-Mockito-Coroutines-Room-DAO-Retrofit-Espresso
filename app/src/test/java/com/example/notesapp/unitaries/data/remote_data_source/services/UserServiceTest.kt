package com.example.notesapp.unitaries.data.remote_data_source.services

import com.example.notesapp.concerns.WebServerMock.WEB_SERVER_MOCK_URL
import com.example.notesapp.concerns.WebServerMock.enqueueResponse
import com.example.notesapp.concerns.WebServerMock.startServer
import com.example.notesapp.concerns.WebServerMock.stopServer
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.constants.data.UserDataConstants.USER_JSON
import com.example.notesapp.constants.data.UserDataConstants.USER_MODEL_OBJECT
import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import com.example.notesapp.data.remote_data_source.services.UserService
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection.HTTP_CREATED

class UserServiceTest {
    @Before
    fun beforeEach() {
        startServer()
    }

    @After
    fun afterEach() {
        stopServer()
    }

    @Test
    fun testIfMethodGetInstanceReturnsWorkingInstance() {
        enqueueResponse(USER_JSON, HTTP_CREATED)

        val userGateway = UserService.getInstance(WEB_SERVER_MOCK_URL)

        runTest {
            val createdUserOnDatabase = userGateway.createUser(USER_MODEL_OBJECT)

            assertEquals(USER_ID, createdUserOnDatabase.id)
            assertEquals(USER_USERNAME, createdUserOnDatabase.username)
        }
    }
}
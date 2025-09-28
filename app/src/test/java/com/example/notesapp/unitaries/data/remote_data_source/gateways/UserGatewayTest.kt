package com.example.notesapp.unitaries.data.remote_data_source.gateways

import com.example.notesapp.concerns.WebServerMock.WEB_SERVER_MOCK_URL
import com.example.notesapp.concerns.WebServerMock.enqueueResponse
import com.example.notesapp.concerns.WebServerMock.startServer
import com.example.notesapp.concerns.WebServerMock.stopServer
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.constants.data.UserDataConstants.USER_JSON
import com.example.notesapp.constants.data.UserDataConstants.USER_MODEL_OBJECT
import com.example.notesapp.constants.data.UserDataConstants.USER_USERNAME
import com.example.notesapp.data.remote_data_source.gateways.UserGateway
import com.example.notesapp.data.remote_data_source.services.UserService
import kotlinx.coroutines.test.runTest
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import java.net.HttpURLConnection.HTTP_CREATED

class UserGatewayTest {
    @Test
    fun testIfMethodCreateUserCreatesUserOnService() {
        enqueueResponse(USER_JSON, HTTP_CREATED)

        runTest {
            val createdUserOnService = userGateway.createUser(USER_MODEL_OBJECT)

            assertEquals(USER_ID, createdUserOnService.id)
            assertEquals(USER_USERNAME, createdUserOnService.username)
        }
    }

    companion object {
        private lateinit var userGateway: UserGateway

        @JvmStatic
        @BeforeClass
        fun beforeAll() {
            startServer()

            userGateway = UserService.getInstance(WEB_SERVER_MOCK_URL)
        }

        @JvmStatic
        @AfterClass
        fun afterEach() {
            stopServer()
        }
    }
}
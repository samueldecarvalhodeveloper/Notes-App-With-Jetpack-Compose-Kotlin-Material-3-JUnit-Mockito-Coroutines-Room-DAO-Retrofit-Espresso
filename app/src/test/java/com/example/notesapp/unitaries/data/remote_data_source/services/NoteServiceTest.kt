package com.example.notesapp.unitaries.data.remote_data_source.services

import com.example.notesapp.concerns.WebServerMock.WEB_SERVER_MOCK_URL
import com.example.notesapp.concerns.WebServerMock.enqueueResponse
import com.example.notesapp.concerns.WebServerMock.startServer
import com.example.notesapp.concerns.WebServerMock.stopServer
import com.example.notesapp.constants.data.NoteDataConstants.LIST_OF_NOTES_JSON
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.data.remote_data_source.services.NoteService
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection.HTTP_OK

class NoteServiceTest {
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
        enqueueResponse(LIST_OF_NOTES_JSON, HTTP_OK)

        val noteGateway = NoteService.getInstance(WEB_SERVER_MOCK_URL)

        runTest {
            val notesFromService = noteGateway.getNotes(USER_ID)

            assertTrue(notesFromService.isNotEmpty())
        }
    }
}
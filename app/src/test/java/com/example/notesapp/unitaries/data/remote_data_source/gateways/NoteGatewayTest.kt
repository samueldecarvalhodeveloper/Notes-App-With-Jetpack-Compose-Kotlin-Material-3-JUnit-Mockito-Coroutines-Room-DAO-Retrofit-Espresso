package com.example.notesapp.unitaries.data.remote_data_source.gateways

import com.example.notesapp.concerns.WebServerMock.WEB_SERVER_MOCK_URL
import com.example.notesapp.concerns.WebServerMock.enqueueResponse
import com.example.notesapp.concerns.WebServerMock.startServer
import com.example.notesapp.concerns.WebServerMock.stopServer
import com.example.notesapp.constants.data.NoteDataConstants.EMPTY_LIST_OF_NOTES_JSON
import com.example.notesapp.constants.data.NoteDataConstants.LIST_OF_NOTES_JSON
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_JSON
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_MODEL_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.data.remote_data_source.gateways.NoteGateway
import com.example.notesapp.data.remote_data_source.services.NoteService
import kotlinx.coroutines.test.runTest
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import java.net.HttpURLConnection.HTTP_CREATED
import java.net.HttpURLConnection.HTTP_NO_CONTENT
import java.net.HttpURLConnection.HTTP_OK

class NoteGatewayTest {
    @Test
    fun testIfMethodGetNotesSuccessFetchAllUserNotesFromService() {
        enqueueResponse(LIST_OF_NOTES_JSON, HTTP_OK)

        runTest {
            val noteFromService = noteGateway.getNotes(USER_ID).first()

            assertEquals(NOTE_ID, noteFromService.id)
            assertEquals(NOTE_TITLE, noteFromService.title)
            assertEquals(NOTE_BODY, noteFromService.body)
            assertEquals(NOTE_CREATED_AT, noteFromService.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteFromService.updatedAt)
            assertEquals(USER_ID, noteFromService.userId)
        }
    }

    @Test
    fun testIfMethodCreateNoteCreatesNotesOnService() {
        enqueueResponse(NOTE_JSON, HTTP_CREATED)

        runTest {
            val createdNoteOnService = noteGateway.createNote(USER_ID, NOTE_MODEL_OBJECT)

            assertEquals(NOTE_ID, createdNoteOnService.id)
            assertEquals(NOTE_TITLE, createdNoteOnService.title)
            assertEquals(NOTE_BODY, createdNoteOnService.body)
            assertEquals(NOTE_CREATED_AT, createdNoteOnService.createdAt)
            assertEquals(NOTE_UPDATED_AT, createdNoteOnService.updatedAt)
            assertEquals(USER_ID, createdNoteOnService.userId)
        }
    }

    @Test
    fun testIfMethodUpdateNoteUpdatesNotesOnService() {
        enqueueResponse(NOTE_JSON, HTTP_OK)

        runTest {
            val updatedNoteFromService =
                noteGateway.updateNote(NOTE_ID, USER_ID, NOTE_MODEL_OBJECT)

            assertEquals(NOTE_ID, updatedNoteFromService.id)
            assertEquals(NOTE_TITLE, updatedNoteFromService.title)
            assertEquals(NOTE_BODY, updatedNoteFromService.body)
            assertEquals(NOTE_CREATED_AT, updatedNoteFromService.createdAt)
            assertEquals(NOTE_UPDATED_AT, updatedNoteFromService.updatedAt)
            assertEquals(USER_ID, updatedNoteFromService.userId)
        }
    }

    @Test
    fun testIfMethodDeleteNoteDeletesNoteOnService() {
        enqueueResponse("", HTTP_NO_CONTENT)
        enqueueResponse(EMPTY_LIST_OF_NOTES_JSON, HTTP_OK)

        runTest {
            noteGateway.deleteNote(NOTE_ID, USER_ID)

            val notesFromService = noteGateway.getNotes(USER_ID)

            assertTrue(notesFromService.isEmpty())
        }
    }

    companion object {
        private lateinit var noteGateway: NoteGateway

        @JvmStatic
        @BeforeClass
        fun beforeAll() {
            startServer()

            noteGateway = NoteService.getInstance(WEB_SERVER_MOCK_URL)
        }

        @JvmStatic
        @AfterClass
        fun afterAll() {
            stopServer()
        }
    }
}
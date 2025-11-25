package com.example.notesapp.components

import com.example.notesapp.concerns.RoomDatabaseFactory.getInstance
import com.example.notesapp.concerns.WebServerMock.WEB_SERVER_MOCK_URL
import com.example.notesapp.concerns.WebServerMock.enqueueResponse
import com.example.notesapp.concerns.WebServerMock.startServer
import com.example.notesapp.concerns.WebServerMock.stopServer
import com.example.notesapp.constants.data.NoteDataConstants.LIST_OF_NOTES_JSON
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ENTITY_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ENTITY_WITH_WRONG_DATA_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_JSON
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject
import com.example.notesapp.data.local_data_source.databases.NoteDatabase
import com.example.notesapp.data.remote_data_source.services.NoteService
import com.example.notesapp.data.repositories.NoteRepository
import kotlinx.coroutines.test.runTest
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.net.HttpURLConnection

@RunWith(RobolectricTestRunner::class)
class NoteComponentTest {
    private lateinit var noteDataAccessObject: NoteDataAccessObject
    private lateinit var noteRepository: NoteRepository

    @Before
    fun beforeEach() {
        val noteDatabase = getInstance(NoteDatabase::class.java)

        noteDataAccessObject = noteDatabase.getDataAccessObject()
        val noteGateway = NoteService.getInstance(WEB_SERVER_MOCK_URL)

        noteRepository = NoteRepository(noteGateway, noteDataAccessObject)
    }

    @Test
    fun fetchingNotesFromServiceAndStoringOnDatabase() {
        enqueueResponse(LIST_OF_NOTES_JSON, HttpURLConnection.HTTP_OK)

        runTest {
            noteRepository.fetchNotesFromService(USER_ID)

            val notesFromDatabase = noteDataAccessObject.getNotes()

            assertTrue(notesFromDatabase.isNotEmpty())
        }
    }

    @Test
    fun fetchingNotesFromDatabase() {
        runTest {
            val notesFromDatabase = noteDataAccessObject.getNotes()

            assertTrue(notesFromDatabase.isEmpty())
        }
    }

    @Test
    fun fetchingNoteFromDatabase() {
        runTest {
            noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT)

            val noteFromDatabase = noteDataAccessObject.getNote(NOTE_ID)

            assertEquals(NOTE_ID, noteFromDatabase.id)
            assertEquals(NOTE_TITLE, noteFromDatabase.title)
            assertEquals(NOTE_BODY, noteFromDatabase.body)
            assertEquals(NOTE_CREATED_AT, noteFromDatabase.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteFromDatabase.updatedAt)
            assertEquals(USER_ID, noteFromDatabase.userId)
        }
    }

    @Test
    fun creatingNoteOnServiceAndOnDatabase() {
        enqueueResponse(NOTE_JSON, HttpURLConnection.HTTP_CREATED)

        runTest {
            noteRepository.getCreatedNote(NOTE_TITLE, NOTE_BODY, USER_ID)

            val noteFromDatabase = noteDataAccessObject.getNote(NOTE_ID)

            assertEquals(NOTE_ID, noteFromDatabase.id)
            assertEquals(NOTE_TITLE, noteFromDatabase.title)
            assertEquals(NOTE_BODY, noteFromDatabase.body)
            assertEquals(NOTE_CREATED_AT, noteFromDatabase.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteFromDatabase.updatedAt)
            assertEquals(USER_ID, noteFromDatabase.userId)
        }
    }

    @Test
    fun updatingNoteOnServiceAndOnDatabase() {
        enqueueResponse(NOTE_JSON, HttpURLConnection.HTTP_OK)
        runTest {
            noteDataAccessObject.createNote(NOTE_ENTITY_WITH_WRONG_DATA_OBJECT)

            noteRepository.getUpdatedNote(NOTE_ID, NOTE_TITLE, NOTE_BODY, USER_ID)

            val noteFromDatabase = noteDataAccessObject.getNote(NOTE_ID)

            assertEquals(NOTE_ID, noteFromDatabase.id)
            assertEquals(NOTE_TITLE, noteFromDatabase.title)
            assertEquals(NOTE_BODY, noteFromDatabase.body)
            assertEquals(NOTE_CREATED_AT, noteFromDatabase.createdAt)
            assertEquals(NOTE_UPDATED_AT, noteFromDatabase.updatedAt)
            assertEquals(USER_ID, noteFromDatabase.userId)
        }
    }

    @Test
    fun deletingNoteOnServiceAndOnDatabase() {
        enqueueResponse("", HttpURLConnection.HTTP_NO_CONTENT)

        runTest {
            noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT)

            noteRepository.deleteNote(NOTE_ID, USER_ID)

            val notesFromDatabase = noteDataAccessObject.getNotes()

            assertTrue(notesFromDatabase.isEmpty())
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
package com.example.notesapp.unitaries.data.local_data_source.data_access_objects

import com.example.notesapp.concerns.RoomDatabaseFactory
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ENTITY_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ENTITY_WITH_WRONG_DATA_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject
import com.example.notesapp.data.local_data_source.databases.NoteDatabase
import com.example.notesapp.data.local_data_source.entities.NoteEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NoteDataAccessObjectTest {
    private lateinit var noteDataAccessObject: NoteDataAccessObject

    @Before
    fun beforeEach() {
        val noteDatabase: NoteDatabase = RoomDatabaseFactory.getInstance(NoteDatabase::class.java)

        noteDataAccessObject = noteDatabase.getDataAccessObject()
    }

    @Test
    fun testIfMethodGetNotesReturnsAllNotesFromDatabase() {
        runTest {
            val notesFromDatabase: List<NoteEntity> = noteDataAccessObject.getNotes()

            assertTrue(notesFromDatabase.isEmpty())
        }
    }

    @Test
    fun testIfMethodGetNoteReturnsNoteFromDatabase() {
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
    fun testIfMethodCreateNoteCreatesNoteOnDatabase() {
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
    fun testIfMethodUpdateNoteUpdatesNoteOnDatabase() {
        runTest {
            noteDataAccessObject.createNote(NOTE_ENTITY_WITH_WRONG_DATA_OBJECT)

            noteDataAccessObject.updateNote(NOTE_ENTITY_OBJECT)

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
    fun testIfMethodDeleteNoteDeletesNoteOnDatabase() {
        runTest {
            noteDataAccessObject.createNote(NOTE_ENTITY_OBJECT)

            noteDataAccessObject.deleteNote(NOTE_ID)

            val notesFromDatabase: List<NoteEntity> = noteDataAccessObject.getNotes()

            assertTrue(notesFromDatabase.isEmpty())
        }
    }
}
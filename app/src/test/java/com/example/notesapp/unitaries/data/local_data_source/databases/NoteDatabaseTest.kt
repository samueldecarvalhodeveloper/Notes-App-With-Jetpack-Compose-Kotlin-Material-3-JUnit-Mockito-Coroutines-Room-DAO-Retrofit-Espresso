package com.example.notesapp.unitaries.data.local_data_source.databases

import com.example.notesapp.concerns.RoomDatabaseFactory.getInstance
import com.example.notesapp.data.local_data_source.databases.NoteDatabase
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NoteDatabaseTest {
    private lateinit var noteDatabase: NoteDatabase

    @Before
    fun beforeEach() {
        noteDatabase = getInstance(NoteDatabase::class.java)
    }

    @Test
    fun testIfMethodGetDataAccessObjectReturnsNoteDataAccessObjectImplementation() {
        runTest {
            val noteDataAccessObject = noteDatabase.getDataAccessObject()

            val notesFromDatabase = noteDataAccessObject.getNotes()

            assertTrue(notesFromDatabase.isEmpty())
        }
    }
}
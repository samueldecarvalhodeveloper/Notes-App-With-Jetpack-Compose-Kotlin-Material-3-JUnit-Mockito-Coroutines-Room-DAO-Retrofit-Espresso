package com.example.notesapp.data.remote_data_source.data_transfer_objects

import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import org.junit.Assert.assertEquals
import org.junit.Test

class NoteDataTransferObjectTest {
    @Test
    fun testIfModelDescribesHowNoteShouldSendDataToTheService() {
        val noteDataTransferObject = NoteDataTransferObject(NOTE_TITLE, NOTE_BODY)

        assertEquals(NOTE_TITLE, noteDataTransferObject.title)
        assertEquals(NOTE_BODY, noteDataTransferObject.body)
    }
}
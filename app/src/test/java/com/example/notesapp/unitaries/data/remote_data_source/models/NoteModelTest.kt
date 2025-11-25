package com.example.notesapp.unitaries.data.remote_data_source.models

import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.data.remote_data_source.models.NoteModel
import org.junit.Assert.assertEquals
import org.junit.Test

class NoteModelTest {
    @Test
    fun testIfModelDescribesHowNoteShouldHoldDataToTheService() {
        val noteModel = NoteModel(NOTE_TITLE, NOTE_BODY)

        assertEquals(NOTE_TITLE, noteModel.title)
        assertEquals(NOTE_BODY, noteModel.body)
    }
}
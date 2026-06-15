package com.example.notesapp.data.models

import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import org.junit.Assert
import org.junit.Test

class NoteTest {
    @Test
    fun testIfMethodGetNoteEntityReturnsCastedEntity() {
        val note = Note(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

        val noteEntity = note.getNoteEntity()

        Assert.assertEquals(NOTE_ID, noteEntity.id)
        Assert.assertEquals(NOTE_TITLE, noteEntity.title)
        Assert.assertEquals(NOTE_BODY, noteEntity.body)
        Assert.assertEquals(NOTE_CREATED_AT, noteEntity.createdAt)
        Assert.assertEquals(NOTE_UPDATED_AT, noteEntity.updatedAt)
        Assert.assertEquals(USER_ID, noteEntity.userId)
    }
}
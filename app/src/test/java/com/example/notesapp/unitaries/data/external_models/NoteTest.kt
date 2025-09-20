package com.example.notesapp.unitaries.data.external_models

import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.data.external_models.Note
import org.junit.Assert
import org.junit.Test

class NoteTest {
    @Test
    fun testIfEntityDescribesHowNoteShouldBeUsedByExternalDomains() {
        val note = Note(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

        Assert.assertEquals(NOTE_ID.toLong(), note.id.toLong())
        Assert.assertEquals(NOTE_TITLE, note.title)
        Assert.assertEquals(NOTE_BODY, note.body)
        Assert.assertEquals(NOTE_CREATED_AT.toLong(), note.createdAt.toLong())
        Assert.assertEquals(NOTE_UPDATED_AT.toLong(), note.updatedAt.toLong())
        Assert.assertEquals(USER_ID.toLong(), note.userId.toLong())
    }

    @Test
    fun testIfMethodGetNoteEntityReturnsCastedDatabaseEntity() {
        val note = Note(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

        val noteEntity = note.getNoteEntity()

        Assert.assertEquals(NOTE_ID.toLong(), noteEntity.id.toLong())
        Assert.assertEquals(NOTE_TITLE, noteEntity.title)
        Assert.assertEquals(NOTE_BODY, noteEntity.body)
        Assert.assertEquals(NOTE_CREATED_AT.toLong(), noteEntity.createdAt.toLong())
        Assert.assertEquals(NOTE_UPDATED_AT.toLong(), noteEntity.updatedAt.toLong())
        Assert.assertEquals(USER_ID.toLong(), noteEntity.userId.toLong())
    }
}
package com.example.notesapp.unitaries.data.local_data_source.entities

import com.example.notesapp.constants.data.NoteDataConstants.NOTE_BODY
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_TITLE
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.data.local_data_source.entities.NoteEntity
import org.junit.Assert
import org.junit.Test

class NoteEntityTest {
    @Test
    fun testIfEntityDescribesHowNoteEntityShouldBeUsed() {
        val noteEntity =
            NoteEntity(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

        Assert.assertEquals(noteEntity.id, NOTE_ID)
        Assert.assertEquals(noteEntity.title, NOTE_TITLE)
        Assert.assertEquals(noteEntity.body, NOTE_BODY)
        Assert.assertEquals(noteEntity.createdAt, NOTE_CREATED_AT)
        Assert.assertEquals(noteEntity.updatedAt, NOTE_UPDATED_AT)
        Assert.assertEquals(noteEntity.userId, USER_ID)
    }

    @Test
    fun testIfMethodGetNoteExternalModelReturnsCastedExternalModel() {
        val noteEntity =
            NoteEntity(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

        val note = noteEntity.getNoteExternalModel()

        Assert.assertEquals(note.id, NOTE_ID)
        Assert.assertEquals(note.title, NOTE_TITLE)
        Assert.assertEquals(note.body, NOTE_BODY)
        Assert.assertEquals(note.createdAt, NOTE_CREATED_AT)
        Assert.assertEquals(note.updatedAt, NOTE_UPDATED_AT)
        Assert.assertEquals(note.userId, USER_ID)
    }
}
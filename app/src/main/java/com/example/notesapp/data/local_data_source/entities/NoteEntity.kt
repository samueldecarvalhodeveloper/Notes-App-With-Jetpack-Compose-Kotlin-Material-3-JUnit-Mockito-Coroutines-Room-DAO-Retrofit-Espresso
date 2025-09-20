package com.example.notesapp.data.local_data_source.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT_COLUMN_NAME
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT_COLUMN_NAME
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_USER_ID_COLUMN_NAME
import com.example.notesapp.data.external_models.Note

@Entity
class NoteEntity(
    @PrimaryKey var id: Int,
    var title: String,
    var body: String,
    @ColumnInfo(
        name = NOTE_CREATED_AT_COLUMN_NAME
    ) var createdAt: Int,
    @ColumnInfo(name = NOTE_UPDATED_AT_COLUMN_NAME) var updatedAt: Int,
    @ColumnInfo(
        name = NOTE_USER_ID_COLUMN_NAME
    ) var userId: Int
) {
    fun getNoteExternalModel() = Note(
        id,
        title,
        body,
        createdAt,
        updatedAt,
        userId
    )
}
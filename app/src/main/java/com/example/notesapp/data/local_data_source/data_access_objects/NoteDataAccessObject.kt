package com.example.notesapp.data.local_data_source.data_access_objects

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notesapp.data.local_data_source.entities.NoteEntity

@Dao
interface NoteDataAccessObject {
    @Query("SELECT * FROM NoteEntity")
    suspend fun getNotes(): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity Where id = :id")
    suspend fun getNote(id: Int): NoteEntity

    @Insert
    suspend fun createNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("DELETE FROM NoteEntity Where id = :id")
    suspend fun deleteNote(id: Int)
}
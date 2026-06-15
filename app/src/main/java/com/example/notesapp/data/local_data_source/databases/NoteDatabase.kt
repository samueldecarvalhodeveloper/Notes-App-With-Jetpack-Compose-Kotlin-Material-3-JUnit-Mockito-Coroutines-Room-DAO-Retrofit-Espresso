package com.example.notesapp.data.local_data_source.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_DATABASE_NAME
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_DATABASE_VERSION
import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject
import com.example.notesapp.data.local_data_source.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = NOTE_DATABASE_VERSION)
abstract class NoteDatabase : RoomDatabase() {
    companion object {
        fun getInstance(context: Context): NoteDatabase {
            return databaseBuilder(
                context,
                NoteDatabase::class.java, NOTE_DATABASE_NAME
            )
                .allowMainThreadQueries()
                .build()
        }
    }

    abstract fun getDataAccessObject(): NoteDataAccessObject
}
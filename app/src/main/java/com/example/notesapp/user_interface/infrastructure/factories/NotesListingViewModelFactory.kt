package com.example.notesapp.user_interface.infrastructure.factories

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.example.notesapp.constants.data.DomainAgnosticConstants.SERVICE_URL
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_DATABASE_NAME
import com.example.notesapp.data.local_data_source.databases.NoteDatabase
import com.example.notesapp.data.remote_data_source.services.NoteService
import com.example.notesapp.data.repositories.NoteRepository
import com.example.notesapp.user_interface.view_models.NotesListingViewModel

object NotesListingViewModelFactory {
    fun getInstance(context: Context): NotesListingViewModel {
        val noteDatabase = databaseBuilder(
            context,
            NoteDatabase::class.java,
            NOTE_DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build()

        val noteDataAccessObject = noteDatabase.getDataAccessObject()
        val noteGateway = NoteService.getInstance(SERVICE_URL)

        val noteRepository = NoteRepository(noteGateway, noteDataAccessObject)

        return NotesListingViewModel(noteRepository)
    }
}
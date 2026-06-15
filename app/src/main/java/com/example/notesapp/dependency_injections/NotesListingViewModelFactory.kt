package com.example.notesapp.dependency_injections

import android.content.Context
import com.example.notesapp.constants.data.DomainAgnosticConstants.SERVICE_URL
import com.example.notesapp.data.local_data_source.databases.NoteDatabase
import com.example.notesapp.data.remote_data_source.services.NoteService
import com.example.notesapp.data.repositories.NoteRepository
import com.example.notesapp.user_interface.view_models.NotesListingViewModel

object NotesListingViewModelFactory {
    fun getInstance(context: Context): NotesListingViewModel {
        val noteDatabase = NoteDatabase.getInstance(context)
        val noteDataAccessObject = noteDatabase.getDataAccessObject()
        val noteGateway = NoteService.getInstance(SERVICE_URL)

        val noteRepository = NoteRepository(noteGateway, noteDataAccessObject)

        return NotesListingViewModel(noteRepository)
    }
}
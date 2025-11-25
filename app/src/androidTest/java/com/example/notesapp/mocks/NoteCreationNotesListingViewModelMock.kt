package com.example.notesapp.mocks

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider
import com.example.notesapp.constants.data.DomainAgnosticConstants.SERVICE_URL
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.data.external_models.Note
import com.example.notesapp.data.local_data_source.databases.NoteDatabase
import com.example.notesapp.data.remote_data_source.services.NoteService
import com.example.notesapp.data.repositories.NoteRepository
import com.example.notesapp.user_interface.view_models.NotesListingViewModel

private val context = ApplicationProvider.getApplicationContext() as Context

private val noteDatabase = inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
    .allowMainThreadQueries()
    .build()

private val noteDataAccessObject = noteDatabase.getDataAccessObject()
private val noteGateway = NoteService.getInstance(SERVICE_URL)

private val noteRepository = NoteRepository(noteGateway, noteDataAccessObject)

open class NoteCreationNotesListingViewModelMock : NotesListingViewModel(noteRepository) {
    private val _listOfNotesState = MutableLiveData<List<Note>>(listOf())
    override val listOfNotesState: LiveData<List<Note>> = _listOfNotesState
    private val _isListOfNotesLoadedState = MutableLiveData(false)
    override val isListOfNotesLoadedState: LiveData<Boolean> = _isListOfNotesLoadedState
    private val _isNoteCreationCurrentlyAbleState = MutableLiveData(false)
    override val isNoteCreationCurrentlyAbleState: LiveData<Boolean> =
        _isNoteCreationCurrentlyAbleState

    override fun loadNotes(userId: Int) {
    }

    override fun createNote(userId: Int, onNoteCreated: (Int) -> Unit) {
        onNoteCreated(NOTE_ID)
    }
}

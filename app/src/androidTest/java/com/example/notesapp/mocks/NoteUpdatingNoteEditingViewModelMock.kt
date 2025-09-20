package com.example.notesapp.mocks

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider
import com.example.notesapp.constants.data.DomainAgnosticConstants.SERVICE_URL
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_CREATED_AT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_OBJECT
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_UPDATED_AT
import com.example.notesapp.constants.data.UserDataConstants.USER_ID
import com.example.notesapp.data.external_models.Note
import com.example.notesapp.data.local_data_source.databases.NoteDatabase
import com.example.notesapp.data.remote_data_source.services.NoteService
import com.example.notesapp.data.repositories.NoteRepository
import com.example.notesapp.user_interface.view_models.NoteEditingViewModel

private val context = ApplicationProvider.getApplicationContext() as Context

private val noteDatabase = inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
    .allowMainThreadQueries()
    .build()

private val noteDataAccessObject = noteDatabase.getDataAccessObject()
private val noteGateway = NoteService.getInstance(SERVICE_URL)

private val noteRepository = NoteRepository(noteGateway, noteDataAccessObject)

open class NoteUpdatingNoteEditingViewModelMock : NoteEditingViewModel(noteRepository) {
    private val _noteState = MutableLiveData(NOTE_OBJECT)
    override val noteState: LiveData<Note> = _noteState
    private val _isNoteManipulationAbleState = MutableLiveData(true)
    override val isNoteManipulationAbleState: LiveData<Boolean> = _isNoteManipulationAbleState
    private val _isNoteLoadedState = MutableLiveData(true)
    override val isNoteLoadedState: LiveData<Boolean> = _isNoteLoadedState
    private val _isNoteBeingManipulatedState = MutableLiveData(true)
    override val isNoteBeingManipulatedState: LiveData<Boolean> = _isNoteBeingManipulatedState
    var noteTitleBeingManipulated: String = ""
    var noteBodyBeingManipulated: String = ""

    override fun loadNote(noteId: Int) {
    }

    override fun manipulateNote() {
    }

    override fun setNoteTitle(title: String) {
        noteTitleBeingManipulated = title
    }

    override fun setNoteBody(body: String) {
        noteBodyBeingManipulated = body
    }

    override fun concludeNote() {
        _noteState.value = Note(NOTE_ID, "", "", NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)
    }

    override fun deleteNote(onNoteDeleted: () -> Unit) {
    }
}
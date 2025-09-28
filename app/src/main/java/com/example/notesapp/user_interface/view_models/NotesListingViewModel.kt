package com.example.notesapp.user_interface.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.external_models.Note
import com.example.notesapp.data.repositories.NoteRepository
import kotlinx.coroutines.launch

open class NotesListingViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _listOfNotesState = MutableLiveData<List<Note>>()
    open val listOfNotesState: LiveData<List<Note>> = _listOfNotesState
    private val _isListOfNotesLoadedState = MutableLiveData(false)
    open val isListOfNotesLoadedState: LiveData<Boolean> = _isListOfNotesLoadedState
    private val _isNoteCreationCurrentlyAbleState = MutableLiveData(true)
    open val isNoteCreationCurrentlyAbleState: LiveData<Boolean> = _isNoteCreationCurrentlyAbleState

    open fun loadNotes(userId: Int) {
        viewModelScope.launch {
            try {
                noteRepository.fetchNotesFromService(userId)
            } catch (_: Exception) {
            } finally {
                val listOfNotesFromDatabase = noteRepository.getNotes()

                _listOfNotesState.value = listOfNotesFromDatabase

                _isListOfNotesLoadedState.value = true
            }
        }
    }

    open fun createNote(userId: Int, onNoteCreated: (Int) -> Unit) {
        viewModelScope.launch {
            try {
                val createdNote = noteRepository.getCreatedNote("", "", userId)

                onNoteCreated(createdNote.id)
            } catch (_: Exception) {
                _isNoteCreationCurrentlyAbleState.value = false
            }
        }
    }
}

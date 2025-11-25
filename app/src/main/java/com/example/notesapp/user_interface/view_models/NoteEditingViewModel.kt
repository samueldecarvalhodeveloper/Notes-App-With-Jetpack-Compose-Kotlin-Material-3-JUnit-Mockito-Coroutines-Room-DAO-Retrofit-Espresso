package com.example.notesapp.user_interface.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.external_models.Note
import com.example.notesapp.data.repositories.NoteRepository
import kotlinx.coroutines.launch

open class NoteEditingViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _noteState = MutableLiveData<Note>()
    open val noteState: LiveData<Note> = _noteState
    private val _isNoteManipulationAbleState: MutableLiveData<Boolean> = MutableLiveData(true)
    open val isNoteManipulationAbleState: LiveData<Boolean> = _isNoteManipulationAbleState
    private val _isNoteLoadedState: MutableLiveData<Boolean> = MutableLiveData(false)
    open val isNoteLoadedState: LiveData<Boolean> = _isNoteLoadedState
    private val _isNoteBeingManipulatedState: MutableLiveData<Boolean> = MutableLiveData(false)
    open val isNoteBeingManipulatedState: LiveData<Boolean> = _isNoteBeingManipulatedState

    open fun loadNote(noteId: Int) {
        viewModelScope.launch {
            val noteFromDatabase = noteRepository.getNote(noteId)

            _noteState.value = noteFromDatabase

            _isNoteLoadedState.value = true
        }
    }

    open fun manipulateNote() {
        _isNoteBeingManipulatedState.value = _isNoteManipulationAbleState.value!!
    }

    open fun setNoteTitle(title: String) {
        _noteState.value!!.title = title
    }

    open fun setNoteBody(body: String) {
        _noteState.value!!.body = body
    }

    open fun concludeNote() {
        viewModelScope.launch {
            try {
                val updatedNoteOnService = noteRepository.getUpdatedNote(
                    _noteState.value!!.id,
                    _noteState.value!!.title,
                    _noteState.value!!.body,
                    _noteState.value!!.userId
                )

                _isNoteManipulationAbleState.value = false
                _isNoteBeingManipulatedState.value = false

                _noteState.value = updatedNoteOnService
            } catch (_: Exception) {
                _isNoteManipulationAbleState.value = false

                _isNoteBeingManipulatedState.value = false
            }
        }
    }

    open fun deleteNote(onNoteDeleted: () -> Unit) {
        viewModelScope.launch {
            try {
                noteRepository.deleteNote(_noteState.value!!.id, _noteState.value!!.userId)

                onNoteDeleted()
            } catch (_: Exception) {
                _isNoteManipulationAbleState.value = false

                _isNoteBeingManipulatedState.value = false
            }
        }
    }
}
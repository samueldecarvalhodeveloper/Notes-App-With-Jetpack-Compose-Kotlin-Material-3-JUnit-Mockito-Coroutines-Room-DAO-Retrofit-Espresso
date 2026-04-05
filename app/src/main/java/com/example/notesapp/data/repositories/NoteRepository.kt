package com.example.notesapp.data.repositories

import com.example.notesapp.data.models.Note
import com.example.notesapp.data.local_data_source.data_access_objects.NoteDataAccessObject
import com.example.notesapp.data.remote_data_source.gateways.NoteGateway
import com.example.notesapp.data.remote_data_source.data_transfer_objects.NoteDataTransferObject

class NoteRepository(
    private val noteGateway: NoteGateway,
    private val noteDataAccessObject: NoteDataAccessObject
) {
    suspend fun synchronizeNotes(userId: Int) {
        val notesFromService = noteGateway.getNotes(userId)

        noteDataAccessObject.deleteNotes()

        for (note in notesFromService) {
            noteDataAccessObject.createNote(note.getNoteEntity())
        }
    }

    suspend fun getNotes(): List<Note> {
        return noteDataAccessObject.getNotes()
            .map { noteEntity -> noteEntity.getNoteExternalModel() }
    }

    suspend fun getNote(id: Int): Note {
        return noteDataAccessObject.getNote(id).getNoteExternalModel()
    }

    suspend fun getCreatedNote(title: String, body: String, userId: Int): Note {
        val noteDataTransferObject = NoteDataTransferObject(title, body)

        val createdNoteOnService = noteGateway.createNote(userId, noteDataTransferObject)

        noteDataAccessObject.createNote(createdNoteOnService.getNoteEntity())

        return createdNoteOnService
    }

    suspend fun getUpdatedNote(id: Int, title: String, body: String, userId: Int): Note {
        val noteDataTransferObject = NoteDataTransferObject(title, body)

        val updatedNoteOnService = noteGateway.updateNote(id, userId, noteDataTransferObject)

        noteDataAccessObject.updateNote(updatedNoteOnService.getNoteEntity())

        return updatedNoteOnService
    }

    suspend fun deleteNote(id: Int, userId: Int) {
        noteGateway.deleteNote(id, userId)

        noteDataAccessObject.deleteNote(id)
    }
}

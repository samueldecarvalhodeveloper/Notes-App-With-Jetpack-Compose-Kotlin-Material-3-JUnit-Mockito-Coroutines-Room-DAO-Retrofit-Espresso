package com.example.notesapp.data.remote_data_source.gateways

import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ID_PATH_PARAMETER
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_ROUTE
import com.example.notesapp.constants.data.NoteDataConstants.NOTE_SPECIFIC_ROUTE
import com.example.notesapp.constants.data.NoteDataConstants.USER_ID_PATH_PARAMETER
import com.example.notesapp.data.external_models.Note
import com.example.notesapp.data.remote_data_source.models.NoteModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface NoteGateway {
    @GET(NOTE_ROUTE)
    suspend fun getNotes(@Path(USER_ID_PATH_PARAMETER) userId: Int): List<Note>

    @POST(NOTE_ROUTE)
    suspend fun createNote(@Path(USER_ID_PATH_PARAMETER) userId: Int, @Body note: NoteModel): Note

    @PATCH(NOTE_SPECIFIC_ROUTE)
    suspend fun updateNote(
        @Path(NOTE_ID_PATH_PARAMETER) noteId: Int,
        @Path(USER_ID_PATH_PARAMETER) userId: Int,
        @Body note: NoteModel
    ): Note

    @DELETE(NOTE_SPECIFIC_ROUTE)
    suspend fun deleteNote(
        @Path(NOTE_ID_PATH_PARAMETER) noteId: Int,
        @Path(USER_ID_PATH_PARAMETER) userId: Int
    )
}
package com.example.notesapp.constants.data

import com.example.notesapp.constants.data.UserDataConstants.USER_ID

import com.example.notesapp.data.external_models.Note
import com.example.notesapp.data.local_data_source.entities.NoteEntity
import com.example.notesapp.data.remote_data_source.models.NoteModel

object NoteDataConstants {
    const val NOTE_DATABASE_VERSION = 1

    const val NOTE_DATABASE_NAME = "note"

    const val NOTE_CREATED_AT_COLUMN_NAME = "created_at"

    const val NOTE_UPDATED_AT_COLUMN_NAME = "updated_at"

    const val NOTE_USER_ID_COLUMN_NAME = "user_id"

    const val NOTE_ROUTE = "/notes/{id}/"

    const val USER_ID_PATH_PARAMETER = "id"

    const val NOTE_SPECIFIC_ROUTE = "/notes/{id}/{note_id}/"

    const val NOTE_ID_PATH_PARAMETER = "note_id"

    const val NOTE_ID = 20

    const val NOTE_TITLE =
        "Title"

    const val NOTE_BODY =
        "Body"

    const val NOTE_CREATED_AT = 0

    const val NOTE_UPDATED_AT = 0

    val NOTE_MODEL_OBJECT = NoteModel(NOTE_TITLE, NOTE_BODY)

    const val LIST_OF_NOTES_JSON = "[{\"id\":" + NOTE_ID +
            ",\"title\":\"" + NOTE_TITLE +
            "\",\"body\":\"" + NOTE_BODY +
            "\",\"createdAt\":" + NOTE_CREATED_AT +
            ",\"updatedAt\":" + NOTE_UPDATED_AT +
            ",\"userId\":" + USER_ID + "}]"

    const val NOTE_JSON = "{\"id\":" + NOTE_ID +
            ",\"title\":\"" + NOTE_TITLE +
            "\",\"body\":\"" + NOTE_BODY +
            "\",\"createdAt\":" + NOTE_CREATED_AT +
            ",\"updatedAt\":" + NOTE_UPDATED_AT +
            ",\"userId\":" + USER_ID + "}"

    val NOTE_ENTITY_WITH_WRONG_DATA_OBJECT =
        NoteEntity(NOTE_ID, "", "", NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

    val NOTE_OBJECT =
        Note(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

    val NOTE_ENTITY_OBJECT =
        NoteEntity(NOTE_ID, NOTE_TITLE, NOTE_BODY, NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)

    const val EMPTY_LIST_OF_NOTES_JSON = "[]"

    val NOTE_WITH_EMPTY_DATA =
        Note(NOTE_ID, "", "", NOTE_CREATED_AT, NOTE_UPDATED_AT, USER_ID)
}

package com.example.notesapp.constants.data

import com.example.notesapp.data.external_models.User
import com.example.notesapp.data.local_data_source.entities.UserEntity
import com.example.notesapp.data.remote_data_source.models.UserModel

object UserDataConstants {
    const val USER_DATABASE_VERSION = 1

    const val USER_DATABASE_NAME = "user"

    const val USER_ROUTE = "/users/"

    const val USER_ID = 10

    const val USER_USERNAME = "Samuel de Carvalho"

    val USER_MODEL_OBJECT = UserModel(USER_USERNAME)

    const val USER_JSON = "{\"id\":$USER_ID,\"username\":\"$USER_USERNAME\"}"

    val USER_ENTITY_OBJECT = UserEntity(USER_ID, USER_USERNAME)

    val USER_EXTERNAL_MODEL_OBJECT = User(USER_ID, USER_USERNAME)
}
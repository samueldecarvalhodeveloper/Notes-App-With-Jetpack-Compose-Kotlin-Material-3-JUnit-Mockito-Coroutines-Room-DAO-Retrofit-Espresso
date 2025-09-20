package com.example.notesapp.data.external_models

import com.example.notesapp.data.local_data_source.entities.UserEntity

class User(val id: Int, val username: String) {
    fun getUserEntity() = UserEntity(id, username)
}
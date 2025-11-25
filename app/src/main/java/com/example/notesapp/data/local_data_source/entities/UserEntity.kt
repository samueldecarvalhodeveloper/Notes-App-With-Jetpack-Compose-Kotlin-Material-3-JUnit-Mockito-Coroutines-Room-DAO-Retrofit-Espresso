package com.example.notesapp.data.local_data_source.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notesapp.data.external_models.User

@Entity
class UserEntity(@PrimaryKey var id: Int, var username: String) {
    fun getUserExternalModel() = User(id, username)
}
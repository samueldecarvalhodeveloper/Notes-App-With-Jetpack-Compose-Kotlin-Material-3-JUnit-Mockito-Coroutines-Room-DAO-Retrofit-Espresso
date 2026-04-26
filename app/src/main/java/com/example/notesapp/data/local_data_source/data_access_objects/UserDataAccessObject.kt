package com.example.notesapp.data.local_data_source.data_access_objects

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.notesapp.data.local_data_source.entities.UserEntity

@Dao
interface UserDataAccessObject {
    @Query("SELECT * FROM UserEntity")
    suspend fun getUsers(): List<UserEntity>

    @Insert
    suspend fun createUser(user: UserEntity)
}
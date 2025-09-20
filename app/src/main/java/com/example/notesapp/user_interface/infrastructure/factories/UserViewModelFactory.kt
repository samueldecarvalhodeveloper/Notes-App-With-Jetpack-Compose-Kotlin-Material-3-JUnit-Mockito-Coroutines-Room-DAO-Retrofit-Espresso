package com.example.notesapp.user_interface.infrastructure.factories

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.example.notesapp.constants.data.DomainAgnosticConstants.SERVICE_URL
import com.example.notesapp.constants.data.UserDataConstants.USER_DATABASE_NAME
import com.example.notesapp.data.local_data_source.databases.UserDatabase
import com.example.notesapp.data.remote_data_source.services.UserService
import com.example.notesapp.data.repositories.UserRepository
import com.example.notesapp.user_interface.view_models.UserViewModel

object UserViewModelFactory {
    private lateinit var instance: UserViewModel

    fun getInstance(context: Context): UserViewModel {
        if (this::instance.isInitialized.not()) {
            val userDatabase =
                databaseBuilder(context, UserDatabase::class.java, USER_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()

            val userDataAccessObject = userDatabase.getDataAccessObject()
            val userGateway = UserService.getInstance(SERVICE_URL)

            val userRepository = UserRepository(userGateway, userDataAccessObject)

            instance = UserViewModel(userRepository)
        }

        return instance
    }
}
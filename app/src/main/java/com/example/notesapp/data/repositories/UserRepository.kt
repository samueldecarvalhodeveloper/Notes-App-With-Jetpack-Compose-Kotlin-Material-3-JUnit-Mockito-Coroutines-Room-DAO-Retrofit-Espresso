package com.example.notesapp.data.repositories

import com.example.notesapp.data.models.User
import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject
import com.example.notesapp.data.remote_data_source.gateways.UserGateway
import com.example.notesapp.data.remote_data_source.data_transfer_objects.UserDataTransferObject

class UserRepository(
    private val userGateway: UserGateway,
    private val userDataAccessObject: UserDataAccessObject
) {
    suspend fun getUser(): User {
        return userDataAccessObject.getUsers().last().getUserModel()
    }

    suspend fun getCreatedUser(username: String): User {
        val userDataTransferObject = UserDataTransferObject(username)

        val createdUserOnService = userGateway.createUser(userDataTransferObject)

        userDataAccessObject.createUser(createdUserOnService.getUserEntity())

        return createdUserOnService
    }
}

package com.example.notesapp.data.remote_data_source.gateways

import com.example.notesapp.constants.data.UserDataConstants.USER_ROUTE
import com.example.notesapp.data.models.User
import com.example.notesapp.data.remote_data_source.data_transfer_objects.UserDataTransferObject
import retrofit2.http.Body
import retrofit2.http.POST

interface UserGateway {
    @POST(USER_ROUTE)
    suspend fun createUser(@Body user: UserDataTransferObject): User
}
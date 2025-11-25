package com.example.notesapp.data.remote_data_source.gateways

import com.example.notesapp.constants.data.UserDataConstants.USER_ROUTE
import com.example.notesapp.data.external_models.User
import com.example.notesapp.data.remote_data_source.models.UserModel
import retrofit2.http.Body
import retrofit2.http.POST

interface UserGateway {
    @POST(USER_ROUTE)
    suspend fun createUser(@Body user: UserModel): User
}
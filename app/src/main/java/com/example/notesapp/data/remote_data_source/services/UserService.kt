package com.example.notesapp.data.remote_data_source.services

import com.example.notesapp.data.remote_data_source.gateways.UserGateway
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserService {
    companion object {
        private lateinit var instance: UserGateway

        fun getInstance(baseUrl: String): UserGateway {
            val converterFactory = GsonConverterFactory.create()

            instance = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .build()
                .create(UserGateway::class.java)

            return instance
        }
    }
}
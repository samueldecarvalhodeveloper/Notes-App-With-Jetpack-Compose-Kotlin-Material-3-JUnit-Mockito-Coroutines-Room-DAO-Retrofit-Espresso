package com.example.notesapp.data.remote_data_source.services

import com.example.notesapp.data.remote_data_source.gateways.UserGateway
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserService {
    companion object {
        private lateinit var instance: UserGateway

        fun getInstance(baseUrl: String): UserGateway {
            if (this::instance.isInitialized.not()) {
                instance = Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(UserGateway::class.java)
            }

            return instance
        }
    }
}
package com.example.notesapp.data.remote_data_source.services

import com.example.notesapp.data.remote_data_source.gateways.NoteGateway
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NoteService {
    companion object {
        private lateinit var instance: NoteGateway

        fun getInstance(baseUrl: String): NoteGateway {
            if (this::instance.isInitialized.not()) {
                instance = Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(NoteGateway::class.java)
            }

            return instance
        }
    }
}
package com.example.notesapp.data.remote_data_source.services

import com.example.notesapp.data.remote_data_source.gateways.NoteGateway
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NoteService {
    companion object {
        private lateinit var instance: NoteGateway

        fun getInstance(baseUrl: String): NoteGateway {
            val converterFactory = GsonConverterFactory.create()

            instance = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .build()
                .create(NoteGateway::class.java)

            return instance
        }
    }
}
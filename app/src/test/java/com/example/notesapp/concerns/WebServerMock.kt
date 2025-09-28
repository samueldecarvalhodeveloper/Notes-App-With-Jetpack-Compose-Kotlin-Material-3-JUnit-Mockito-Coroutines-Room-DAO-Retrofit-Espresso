package com.example.notesapp.concerns

import com.example.notesapp.constants.data.DomainAgnosticConstants.JSON_CONTENT_TYPE_HEADER
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

object WebServerMock {
    private val webServerMock = MockWebServer()

    lateinit var WEB_SERVER_MOCK_URL: String

    fun startServer() {
        try {
            webServerMock.start()

            WEB_SERVER_MOCK_URL = webServerMock.url("").toString()
        } catch (ignored: Exception) {
        }
    }

    fun stopServer() {
        try {
            webServerMock.shutdown()
        } catch (ignored: Exception) {
        }
    }

    fun enqueueResponse(body: String, responseCode: Int) {
        val response = MockResponse()
            .addHeader(JSON_CONTENT_TYPE_HEADER)
            .setResponseCode(responseCode)
            .setBody(body)

        webServerMock.enqueue(response)
    }
}
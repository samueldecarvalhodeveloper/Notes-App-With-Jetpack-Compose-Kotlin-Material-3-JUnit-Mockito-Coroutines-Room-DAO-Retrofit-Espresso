package com.example.notesapp.concerns

import com.example.notesapp.constants.data.DomainAgnosticConstants.JSON_CONTENT_TYPE_HEADER
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

object WebServerMock {
    private lateinit var webServerMock: MockWebServer

    lateinit var WEB_SERVER_MOCK_URL: String

    fun startServer() {
        webServerMock = MockWebServer()

        try {
            webServerMock.start()
        } catch (_: Exception) {
        }

        WEB_SERVER_MOCK_URL = webServerMock.url("").toString()
    }

    fun stopServer() {
        try {
            webServerMock.shutdown()
        } catch (_: Exception) {
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
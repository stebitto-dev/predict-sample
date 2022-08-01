package com.stebitto.agify.impl.source.remote

import com.stebitto.network.AndroidHttpClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

internal class AgifyApiService @Inject constructor(
    @AndroidHttpClient private val httpClient: HttpClient
) {

    suspend fun getAgePredictionForName(name: String): AgifyResponseModel =
        httpClient.get(AGIFY_BASE_URL) {
            url { parameters.append("name", name) }
        }.body()

    companion object {
        private const val AGIFY_BASE_URL = "https://api.agify.io"
    }
}
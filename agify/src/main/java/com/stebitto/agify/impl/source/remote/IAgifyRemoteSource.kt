package com.stebitto.agify.impl.source.remote

internal interface IAgifyRemoteSource {
    suspend fun getAgePredictionForName(name: String): AgifyResponseModel
}
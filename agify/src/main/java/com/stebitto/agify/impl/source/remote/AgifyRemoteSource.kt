package com.stebitto.agify.impl.source.remote

import javax.inject.Inject

internal class AgifyRemoteSource @Inject constructor(
    private val agifyApiService: AgifyApiService
) : IAgifyRemoteSource {
    override suspend fun getAgePredictionForName(name: String) = agifyApiService.getAgePredictionForName(name)
}
package com.stebitto.agify.impl.source

import com.stebitto.agify.api.IAgifyRepository
import com.stebitto.agify.impl.source.remote.AgifyRemoteSource
import com.stebitto.commondto.AgifyDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

internal class AgifyRepository @Inject constructor(
    private val agifyRemoteSource: AgifyRemoteSource,
    private val dataMapper: AgifyDataMapper
) : IAgifyRepository {

    override suspend fun getAgePredictionForName(name: String): Flow<AgifyDTO> =
        flowOf(dataMapper.map(agifyRemoteSource.getAgePredictionForName(name)))
}
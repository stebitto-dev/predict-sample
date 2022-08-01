package com.stebitto.agify.impl.source

import com.stebitto.agify.api.IAgifyRepository
import com.stebitto.agify.impl.source.remote.AgifyRemoteSource
import com.stebitto.commondto.AgifyDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class AgifyRepository @Inject constructor(
    private val agifyRemoteSource: AgifyRemoteSource,
    private val dataMapper: AgifyDataMapper
) : IAgifyRepository {

    override fun getAgePredictionForName(name: String): Flow<AgifyDTO> = flow {
        val prediction = dataMapper.map(agifyRemoteSource.getAgePredictionForName(name))
        emit(prediction)
    }
}
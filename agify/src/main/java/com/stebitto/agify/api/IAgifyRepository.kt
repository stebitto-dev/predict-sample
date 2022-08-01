package com.stebitto.agify.api

import com.stebitto.commondto.AgifyDTO
import kotlinx.coroutines.flow.Flow

interface IAgifyRepository {
    suspend fun getAgePredictionForName(name: String): Flow<AgifyDTO>
}
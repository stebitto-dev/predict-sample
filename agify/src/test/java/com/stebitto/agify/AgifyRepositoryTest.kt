package com.stebitto.agify

import com.google.common.truth.Truth.assertThat
import com.stebitto.agify.impl.source.AgifyDataMapper
import com.stebitto.agify.impl.source.AgifyRepository
import com.stebitto.agify.impl.source.remote.AgifyResponseModel
import com.stebitto.agify.impl.source.remote.IAgifyRemoteSource
import com.stebitto.commonexception.GenericException
import com.stebitto.commonexception.NetworkException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class AgifyRepositoryTest {

    private lateinit var repositoryTest: AgifyRepository

    @Test
    fun getPrediction_valid() = runTest {
        val fakeValidSource = object : IAgifyRemoteSource {
            override suspend fun getAgePredictionForName(name: String) =
                AgifyResponseModel("test", 1)
        }

        repositoryTest = AgifyRepository(fakeValidSource, AgifyDataMapper())
        repositoryTest.getAgePredictionForName("").collect { result ->
            assertThat(result.isSuccess).isTrue()
            result.onSuccess {
                assertThat(it.name).isEqualTo("test")
                assertThat(it.age).isEqualTo(1)
            }
        }
    }

    @Test
    fun getPrediction_NetworkException() = runTest {
        val fakeExceptionSource = object : IAgifyRemoteSource {
            override suspend fun getAgePredictionForName(name: String): AgifyResponseModel {
                throw UnknownHostException()
            }
        }

        repositoryTest = AgifyRepository(fakeExceptionSource, AgifyDataMapper())
        repositoryTest.getAgePredictionForName("").collect { result ->
            assertThat(result.isFailure).isTrue()
            result.onFailure { assertThat(it).isEqualTo(NetworkException) }
        }
    }

    @Test
    fun getPrediction_GenericException() = runTest {
        val fakeExceptionSource = object: IAgifyRemoteSource {
            override suspend fun getAgePredictionForName(name: String): AgifyResponseModel {
                throw RuntimeException()
            }
        }

        repositoryTest = AgifyRepository(fakeExceptionSource, AgifyDataMapper())
        repositoryTest.getAgePredictionForName("").collect { result ->
            assertThat(result.isFailure).isTrue()
            result.onFailure { assertThat(it).isEqualTo(GenericException) }
        }
    }
}
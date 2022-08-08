package com.stebitto.agify

import com.google.common.truth.Truth.assertThat
import com.stebitto.agify.api.IAgifyRepository
import com.stebitto.agify.impl.ui.AgifyViewModel
import com.stebitto.commondto.AgifyDTO
import com.stebitto.commonexception.GenericException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class AgifyViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModelTest: AgifyViewModel

    @Test
    fun test_Init_state() = runTest {
        val fakeRepository = object : IAgifyRepository {
            override fun getAgePredictionForName(name: String): Flow<Result<AgifyDTO>> {
                return flowOf(Result.success(AgifyDTO("", 0)))
            }
        }

        viewModelTest = AgifyViewModel(fakeRepository)

        assertThat(viewModelTest.state.value).isEqualTo(AgifyViewModel.State.Init)
    }

    @Test
    fun test_Success_state() = runTest {
        val agifyDTO = AgifyDTO("", 0)
        val fakeRepository = object : IAgifyRepository {
            override fun getAgePredictionForName(name: String): Flow<Result<AgifyDTO>> {
                return flowOf(Result.success(agifyDTO))
            }
        }

        viewModelTest = AgifyViewModel(fakeRepository)
        viewModelTest.getPredictionForName("")

        assertThat(viewModelTest.state.value)
            .isEqualTo(AgifyViewModel.State.Success(agifyDTO.name, agifyDTO.age))
    }

    @Test
    fun test_Error_state() = runTest {
        val fakeRepository = object : IAgifyRepository {
            override fun getAgePredictionForName(name: String): Flow<Result<AgifyDTO>> {
                return flowOf(Result.failure(GenericException))
            }
        }

        viewModelTest = AgifyViewModel(fakeRepository)
        viewModelTest.getPredictionForName("")

        assertThat(viewModelTest.state.value)
            .isEqualTo(AgifyViewModel.State.Error(GenericException.code, GenericException.text))
    }
}


// Reusable JUnit4 TestRule to override the Main dispatcher
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
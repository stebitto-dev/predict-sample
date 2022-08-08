package com.stebitto.agify.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stebitto.agify.api.IAgifyRepository
import com.stebitto.commonexception.HttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AgifyViewModel @Inject constructor(
    private val agifyRepository: IAgifyRepository
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Init)
    val state: StateFlow<State> get() = _state

    fun getPredictionForName(name: String) = viewModelScope.launch {
        agifyRepository.getAgePredictionForName(name)
            .onStart { _state.update { State.Loading } }
            .collect { result ->
                result.onSuccess { prediction ->
                    _state.update { State.Success(prediction.name, prediction.age) }
                }
                result.onFailure { error ->
                    with(error as HttpException) {
                        _state.update { State.Error(code, text) }
                    }
                }
            }
    }

    sealed class State {
        object Init : State()
        object Loading : State()
        data class Success(
            val name: String,
            val age: Int
        ) : State()
        data class Error(
            val code: Int,
            val message: String
        ) : State()
    }
}
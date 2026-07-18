package com.example.talkiesocial.core.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Effect> : ViewModel() {

    abstract val initialState: State
    private val _uiState by lazy { MutableStateFlow(initialState) }
    val uiState by lazy { _uiState.asStateFlow() }

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    protected fun updateState(update: (State) -> State) {
        _uiState.update(update)
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    protected fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}

sealed interface CommonEffect {
    interface ShowSnackbar : CommonEffect {
        val message: String
    }
}

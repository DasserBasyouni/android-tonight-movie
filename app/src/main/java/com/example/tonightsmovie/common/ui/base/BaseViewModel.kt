package com.example.tonightsmovie.common.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


abstract class BaseViewModel : ViewModel() {
    private val _baseUiState = Channel<BaseUiState>(Channel.BUFFERED)
    val baseEvent = _baseUiState.receiveAsFlow()

    fun <T> Flow<T>.toStateFlow(initValue: T): StateFlow<T> =
        this.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initValue)

    fun onBackClick() = updateState(_baseUiState, BaseUiState.Back)

    fun <T> updateState(channel: Channel<T>, event: T) {
        viewModelScope.launch {
            channel.send(event)
        }
    }

    fun <T, M> StateFlow<T>.stateFlowMap(
        mapper: (value: T) -> M
    ): StateFlow<M> = map { mapper(it) }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), mapper(value)
    )
}
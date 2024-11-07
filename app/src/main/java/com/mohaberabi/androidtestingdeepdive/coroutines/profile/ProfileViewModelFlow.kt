package com.mohaberabi.androidtestingdeepdive.coroutines.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.androidtestingdeepdive.common.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed interface ProfileFlowState {
    data object Idle : ProfileFlowState

    data object Loading : ProfileFlowState
    data class Done(val profile: Profile) : ProfileFlowState
    data class Error(val error: String) : ProfileFlowState
}

class ProfileViewModelFlow(
    private val getProfileFlow: GetProfileFlowUsCase
) : ViewModel() {


    private val _state = MutableStateFlow<ProfileFlowState>(ProfileFlowState.Idle)
    val state = _state.asStateFlow()


    init {

        getProfileFlow()
            .onStart {
                _state.update { ProfileFlowState.Loading }
            }.onEach { result ->
                result.onFailure { error ->
                    _state.update { ProfileFlowState.Error(error.message ?: "some error") }
                }.onSuccess { prof ->
                    _state.update { ProfileFlowState.Done(prof) }
                }
            }.launchIn(viewModelScope)
    }


}
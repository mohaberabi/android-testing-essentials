package com.mohaberabi.androidtestingdeepdive.coroutines.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.androidtestingdeepdive.common.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed interface ProfileState {
    data object Idle : ProfileState

    data object Loading : ProfileState
    data class Done(val profile: Profile) : ProfileState
    data class Error(val error: String) : ProfileState
}

class ProfileViewModel(
    private val getProfile: GetProfileUseCase
) : ViewModel() {


    private val _state = MutableStateFlow<ProfileState>(ProfileState.Idle)
    val state = _state.asStateFlow()

    init {
        getUserProfile()
    }

    fun getUserProfile() {
        viewModelScope.launch {
            _state.update { ProfileState.Loading }
            runCatching {
                getProfile()
            }.onSuccess { profile ->
                _state.update { ProfileState.Done(profile) }
            }.onFailure { error ->
                _state.update { ProfileState.Error(error.message ?: "error") }
            }
        }
    }
}
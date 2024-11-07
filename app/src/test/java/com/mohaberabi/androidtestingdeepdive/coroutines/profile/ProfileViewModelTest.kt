package com.mohaberabi.androidtestingdeepdive.coroutines.profile

import com.mohaberabi.androidtestingdeepdive.common.Friend
import com.mohaberabi.androidtestingdeepdive.common.Profile
import com.mohaberabi.androidtestingdeepdive.coroutines.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest {


    @get:Rule
    val mainRule = MainDispatcherRule(StandardTestDispatcher())

    private lateinit var viewmodel: ProfileViewModel
    private lateinit var getProfile: GetProfileUseCase

    companion object {
        val profile = Profile("mohab", 0f, listOf(Friend("", "")))

    }


    @Test
    fun ` when get profile called emits loading , done with the profile instance`() = runTest {
        getProfile = mockk()
        coEvery { getProfile() } coAnswers { profile }
        viewmodel = ProfileViewModel(getProfile)
        advanceUntilIdle()
        assertEquals(ProfileState.Done(profile), viewmodel.state.value)

    }

    @Test
    fun ` when get profile called emits loading , emits error `() = runTest {
        getProfile = mockk()
        coEvery { getProfile() } throws IllegalArgumentException("error")
        viewmodel = ProfileViewModel(getProfile)
        advanceUntilIdle()
        assertEquals(ProfileState.Error("error"), viewmodel.state.value)

    }
}
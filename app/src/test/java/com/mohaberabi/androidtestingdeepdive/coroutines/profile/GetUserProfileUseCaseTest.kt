package com.mohaberabi.androidtestingdeepdive.coroutines.profile

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

class GetUserProfileUseCaseTest {


    private lateinit var getProfile: GetProfileUseCase

    private lateinit var repository: ProfileRepository

    @Before
    fun setup() {
        repository = FakeProfileRepository()
        getProfile = GetProfileUseCase(repository)
    }

    @Test
    fun ` when get user profile  returns correctly sequential no delays `() = runTest {
        val profile = getProfile()
        assertEquals(profile.rate, -100f)
        assertEquals(profile.name, "test")
        assertEquals(profile.friends.size, 1)

    }

    @Test
    fun ` when get user profile  returns correctly parallel  with delay`() = runTest {
        val repov2 = FakeProfileRepositoryV2()
        val getProfile2 = GetProfileUseCase(repov2)
        val profile = getProfile2()
        assertEquals(profile.rate, -100f)
        assertEquals(profile.name, "test")
        assertEquals(profile.friends.size, 1)
        assertEquals(1000L, currentTime)
    }

    @Test
    fun ` when get user profile  returns correctly sequential with delay`() = runTest {
        val repov2 = FakeProfileRepositoryV2()
        val getProfile2 = GetProfileUseCase2(repov2)
        val profile = getProfile2()
        assertEquals(profile.rate, -100f)
        assertEquals(profile.name, "test")
        assertEquals(profile.friends.size, 1)
        assertEquals(2000L, currentTime)
    }
}
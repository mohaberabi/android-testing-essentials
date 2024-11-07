package com.mohaberabi.androidtestingdeepdive.coroutines.profile


import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.coroutines.ContinuationInterceptor
import kotlin.system.measureTimeMillis

class GetUserProfileUseCaseSwitchedDispatcherTest {


    @Test
    fun ` when get user profile  returns correctly sequential no delays `() = runTest {

        val repository = FakeProfileRepository()
        val context = this.coroutineContext
        val dispatcher = context[ContinuationInterceptor] as CoroutineDispatcher
        val getProfile = GetProfileUseCaseSwitchDispatcher(repository, dispatcher)

        val profile = getProfile()
        assertEquals(profile.rate, -100f)
        assertEquals(profile.name, "test")
        assertEquals(profile.friends.size, 1)

    }

    @Test
    fun ` when get user profile  returns correctly parallel  with delay`() = runTest {
        val repov2 = FakeProfileRepositoryV2()
        val context = this.coroutineContext
        val dispatcher = context[ContinuationInterceptor] as CoroutineDispatcher
        val getProfile2 = GetProfileUseCaseSwitchDispatcher(repov2, dispatcher)
        val profile = getProfile2()
        assertEquals(profile.rate, -100f)
        assertEquals(profile.name, "test")
        assertEquals(profile.friends.size, 1)
        assertEquals(2000L, currentTime)
    }

    @Test
    fun ` when get user profile  returns correctly sequential with delay`() = runTest {
        val repov2 = FakeProfileRepositoryV2()
        val context = this.coroutineContext
        val dispatcher = context[ContinuationInterceptor] as CoroutineDispatcher
        val getProfile2 = GetProfileUseCaseSwitchDispatcher(repov2, dispatcher)
        val profile = getProfile2()
        assertEquals(profile.rate, -100f)
        assertEquals(profile.name, "test")
        assertEquals(profile.friends.size, 1)
        assertEquals(2000L, currentTime)
    }
}
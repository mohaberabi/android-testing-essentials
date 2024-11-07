package com.mohaberabi.androidtestingdeepdive.coroutines.profile

import app.cash.turbine.test
import com.mohaberabi.androidtestingdeepdive.common.Profile
import com.mohaberabi.androidtestingdeepdive.shouldBeEqualTo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.coroutines.ContinuationInterceptor

class GetProfileFlowUseCaseTest {


    @Test

    fun `when repo returns well emits done `() = runTest {


        val repo = mockk<ProfileRepository>()

        coEvery { repo.getRate() } coAnswers { 1f }
        coEvery { repo.getName("") } coAnswers { "name" }
        coEvery { repo.getFriends() } coAnswers { listOf() }
        val getProfileFlow = GetProfileFlowUsCase(repo)

        getProfileFlow().test {

            awaitItem()
                .onSuccess {
                    it shouldBeEqualTo Profile("name", 1f, listOf())
                }
            awaitComplete()
        }
    }

    @Test

    fun `when repo returns error  emits failure `() = runTest {


        val repo = mockk<ProfileRepository>()

        coEvery { repo.getRate() } coAnswers { 1f }
        coEvery { repo.getName("") } coAnswers { "name" }
        coEvery { repo.getFriends() } coAnswers { throw Exception("by me") }
        val getProfileFlow = GetProfileFlowUsCase(
            repo,
            this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
        )

        getProfileFlow().collect {
            it.isFailure shouldBeEqualTo true
        }
    }

    @Test

    fun `when repo returns error  tries  then emits done  `() = runTest {


        var throwError = true
        val repo = mockk<ProfileRepository>()

        coEvery { repo.getRate() } coAnswers { 1f }
        coEvery { repo.getName("") } coAnswers { "name" }
        coEvery { repo.getFriends() } coAnswers { if (throwError) throw Exception("by me") else listOf() }
        val getProfileFlow = GetProfileFlowUsCase(
            repo,
            this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
        )

        launch {
            getProfileFlow()
        }
        advanceTimeBy(1000L)
        throwError = false
        advanceTimeBy(1000)
    }
}
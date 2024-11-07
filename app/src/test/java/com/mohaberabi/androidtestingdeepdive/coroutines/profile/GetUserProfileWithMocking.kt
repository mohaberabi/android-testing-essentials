package com.mohaberabi.androidtestingdeepdive.coroutines.profile


import com.mohaberabi.androidtestingdeepdive.common.Friend
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.system.measureTimeMillis

class GetUserProfileUseCaseTestWithMocking {


    private lateinit var getProfile: GetProfileUseCase

    private lateinit var repository: ProfileRepository

    @Before
    fun setup() {
        repository = mockk()
        getProfile = GetProfileUseCase(repository)
    }

    @Test
    fun ` when get user profile  returns correctly sequential no delay`() = runTest {
        coEvery { repository.getRate() } coAnswers { -100f }
        coEvery { repository.getFriends() } coAnswers { listOf(Friend("", "")) }
        coEvery { repository.getName("test") } coAnswers { "test" }
        val profile = getProfile()
        assertEquals(profile.rate, -100f)
        assertEquals(profile.name, "test")
        assertEquals(profile.friends.size, 1)

    }

    @Test
    fun ` when get user profile  returns correctly parallel `() = runTest {
        coEvery { repository.getRate() } coAnswers {
            delay(1000)
            -100f
        }
        coEvery { repository.getFriends() } coAnswers {
            delay(1000L)
            listOf(Friend("", ""))
        }
        coEvery { repository.getName("test") } coAnswers { "test" }
        val profile = getProfile()
        assertEquals(profile.rate, -100f)
        assertEquals(profile.name, "test")
        assertEquals(profile.friends.size, 1)
        assertEquals(1000L, currentTime)

    }

    @Test
    fun ` when get user profile  returns correctly sequential delay`() = runTest {
        val repov2 = mockk<FakeProfileRepositoryV2>()
        coEvery { repov2.getRate() } coAnswers {
            delay(1000)
            -100f
        }
        coEvery { repov2.getFriends() } coAnswers {
            delay(1000L)
            listOf(Friend("", ""))
        }
        coEvery { repov2.getName("test") } coAnswers { "test" }
        val getProfile2 = GetProfileUseCase2(repov2)
        val profile = getProfile2()

        assertEquals(profile.rate, -100f)
        assertEquals(profile.name, "test")
        assertEquals(profile.friends.size, 1)
        assertEquals(2000L, currentTime)
    }
}
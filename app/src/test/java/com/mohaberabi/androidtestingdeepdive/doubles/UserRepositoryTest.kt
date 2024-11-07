package com.mohaberabi.androidtestingdeepdive.doubles

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UserRepositoryTest {


    @Test


    fun ` testing mocking 1`() {


        val userSource: UsersSource = mockk()
        val repo = UserRepository(userSource)

        every { userSource.getUsersCount() } returns 100

        val res = repo.getUsersCount()
        assertEquals(res, 100)

    }

    @Test
    fun ` testing mocking 2`() {


        val userSource: UsersSource = mockk()
        val repo = UserRepository(userSource)

        every { userSource.getUsersCount() } returns -200

        val res = repo.getUsersCount()
        assertEquals(res, -200)
        verify(atMost = 1) {
            userSource.getUsersCount()
        }
    }

    @Test
    fun ` testing spying  `() {
        val userSource: UsersSource = RealUserSource()
        val userSpy = spyk<UsersSource>(userSource)
        val repo = UserRepository(userSpy)
        val res = repo.getUsersCount()
        assertEquals(res, 100)
        verify(atMost = 1) {
            userSpy.getUsersCount()
        }
    }

}
package com.mohaberabi.androidtestingdeepdive.doubles

import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MyCalculatorTest {


    @Test
    fun ` test strict mocking`() {
        val dep1 = mockk<Dep1>()
        val dep2 = mockk<Dep2>()
        every { dep1.value } returns 1
        every { dep2.value } returns 6
        val calculator = MyCalculator(dep1, dep2)
        val res = calculator.add()
        assertEquals(res, 7)
    }

    @Test
    fun ` test relaxed  mocking`() {
        val dep1 = mockk<Dep1>(relaxed = true)
        val dep2 = mockk<Dep2>(relaxed = true)
        every { dep2.value } returns 3
        val calculator = MyCalculator(dep1, dep2)
        val res = calculator.add()
        assertEquals(res, 3)
    }

    @Test
    fun ` test relaxed  mocking 2 `() {
        val dep1 = mockk<Dep1>(relaxed = true)
        val dep2 = mockk<Dep2>(relaxed = true)
        val calculator = MyCalculator(dep1, dep2)
        val res = calculator.add()
        assertEquals(res, 0)
    }

    @Test
    fun ` test spy stubbing`() {
        val dep1 = mockk<Dep1>(relaxed = true)
        val dep2 = mockk<Dep2>(relaxed = true)
        val calculator = MyCalculator(dep1, dep2)
        val res = calculator.add()
        assertEquals(res, 0)
    }
}
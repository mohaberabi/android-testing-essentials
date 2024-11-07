package com.mohaberabi.androidtestingdeepdive.coroutines.cold_flow

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ColdFlowExampleTest {


    private lateinit var coldFlowExample: ColdFlowExample


    @Before
    fun setup() {
        coldFlowExample = ColdFlowExample()
    }


    @Test
    fun `simple flow emits correctly`() = runTest {
        val result = coldFlowExample.simpleFlow().toList()
        assertEquals(result, listOf(1, 2, 3, 4, 5, 6))

    }

    @Test
    fun ` collector collects correctly`() = runTest {
        val expected = mutableListOf<Int>()
        coldFlowExample.simpleFlow().collect {
            expected.add(it)
        }

        assertEquals(expected, listOf(1, 2, 3, 4, 5, 6))

    }

    @Test
    fun `emitter emits and collector collects `() = runTest {
        val expected = mutableListOf<Int>()
        val flow = flow {

            for (i in 1..5) {
                emit(i)
                kotlinx.coroutines.delay(500L)
            }
        }
        flow.onEach {
            expected.add(it)
        }.launchIn(this)
        advanceTimeBy(2500L)

        assertEquals(expected, listOf(1, 2, 3, 4, 5))

    }

    @Test
    fun `emitter emits and collector collects 2`() = runTest {
        val expected = mutableListOf<Int>()
        val flow = flow {

            for (i in 1..5) {
                emit(i)
                kotlinx.coroutines.delay(500L)
            }
        }
        flow.onEach {
            expected.add(it)
        }.launchIn(this)
        advanceUntilIdle()
        assertEquals(expected, listOf(1, 2, 3, 4, 5))

    }

    @Test
    fun `emitter emits and collector collects with excpetion`() = runTest {
        val expected = mutableListOf<Int>()
        val flow = flow {

            for (i in 1..5) {
                if (i == 4) {
                    throw Exception("thrown by me")
                }
                emit(i)
                kotlinx.coroutines.delay(500L)

            }
        }
        flow.onEach {
            expected.add(it)
        }.catch { }.launchIn(this)
        advanceUntilIdle()
        assertEquals(expected, listOf(1, 2, 3))

    }


}
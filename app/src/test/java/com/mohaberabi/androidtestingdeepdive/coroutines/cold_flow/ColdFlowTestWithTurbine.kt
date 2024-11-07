package com.mohaberabi.androidtestingdeepdive.coroutines.cold_flow


import app.cash.turbine.test
import com.mohaberabi.androidtestingdeepdive.shouldBeEqualTo
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
 
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ColdFlowExampleWithTurbineTest {


    private lateinit var coldFlowExample: ColdFlowExample


    @Before
    fun setup() {
        coldFlowExample = ColdFlowExample()
    }


    @Test
    fun `simple flow emits correctly`() = runTest {
        val result = coldFlowExample.simpleFlow()
        result.test {
            assertEquals(1, awaitItem())
            assertEquals(2, awaitItem())
            assertEquals(3, awaitItem())
            assertEquals(4, awaitItem())
            assertEquals(5, awaitItem())
            assertEquals(6, awaitItem())
            awaitComplete()

        }

    }

    @Test
    fun ` collector collects correctly`() = runTest {
        val expected = mutableListOf<Int>()



        coldFlowExample.simpleFlow().test {

            expected.add(awaitItem())
            expected.add(awaitItem())
            expected.add(awaitItem())
            expected.add(awaitItem())
            expected.add(awaitItem())
            expected.add(awaitItem())
            awaitComplete()
        }

        assertEquals(expected, listOf(1, 2, 3, 4, 5, 6))

    }

    @Test
    fun `emitter emits and collector collects with excpetion`() = runTest {

        val flow = flow {

            for (i in 1..5) {
                if (i == 4) {
                    throw Exception("thrown by me")
                }
                emit(i)
                kotlinx.coroutines.delay(500L)

            }
        }

        flow.test {
            assertEquals(1, awaitItem())
            assertEquals(2, awaitItem())
            assertEquals(3, awaitItem())
            assertEquals("thrown by me", awaitError().message)
        }
    }

    @Test
    fun `emitter emits and collector collects with excpetion with some utility`() = runTest {

        val flow = flow {

            for (i in 1..5) {
                if (i == 4) {
                    throw Exception("thrown by me")
                }
                emit(i)
                kotlinx.coroutines.delay(500L)

            }
        }

        flow.test {
            1 shouldBeEqualTo awaitItem()
            2 shouldBeEqualTo awaitItem()
            3 shouldBeEqualTo awaitItem()
            "thrown by me" shouldBeEqualTo awaitError().message

        }
    }


}
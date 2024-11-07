package com.mohaberabi.androidtestingdeepdive.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ExploreUnconfinedTestDispatcher {


    @Test
    fun ` test how eager is the unconfiend test disaptcher`() =
        runTest(UnconfinedTestDispatcher()) {


            launch {
                delay(90909090L)
                println("i will be printed eagerly 1")
            }
            launch {
                delay(1000L)
                println("i will be printed eagerly 2")
            }
            launch {
                delay(500L)
                println("i will be printed eagerly 3")
            }
            launch {
                delay(250L)
                println("i will be printed eagerly 4")
            }
            println()
            //i will be printed eagerly 4
            //i will be printed eagerly 3
            //i will be printed eagerly 2
            //i will be printed eagerly 1
        }


}
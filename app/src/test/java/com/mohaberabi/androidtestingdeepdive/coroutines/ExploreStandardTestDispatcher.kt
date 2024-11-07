package com.mohaberabi.androidtestingdeepdive.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import org.junit.Test


class ExploreStandardTestDispatcher {


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `examine advanceUntilIdle()`() {

        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)
        CoroutineScope(dispatcher).launch {
            println("some work 1 -1")
            delay(1000)
            println("some work 2 -1")
            delay(1000)
            print("coroutine 1 done")
        }

        CoroutineScope(dispatcher).launch {
            delay(500)
            println("some work 2 -2")

        }

        println("${scheduler.currentTime} Before")
        scheduler.advanceUntilIdle()
        println("${scheduler.currentTime} After")

        //some work 1 -1
        //some work 2 -2
        //some work 2 -1
        //coroutine 1 done
        // will take 2000 total time needed to execute whole coroutines
    }

    @Test
    fun `examine advanceTimeBy()`() {

        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)
        CoroutineScope(dispatcher).launch {
            println("some work 1 -1")
            delay(1000)
            println("some work 2 -1")
            delay(1000)
            print("coroutine 1 done")
        }

        scheduler.advanceTimeBy(1000L)
        CoroutineScope(dispatcher).launch {
            delay(500)
            println("some work 2 -2")
        }
        scheduler.advanceTimeBy(1000L)
        println("Guess What ")
        scheduler.advanceTimeBy(1000L)

        //some work 1 -1
        //some work 2 -1
        //some work 2 -2
        //Guess What
        //coroutine 1 done

    }


    @Test
    fun `examine advanceTimeBy 2 ()`() {

        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)
        CoroutineScope(dispatcher).launch {
            delay(500L)
            println("Coroutine1")
        }
        CoroutineScope(dispatcher).launch {
            delay(500L)
            println("Coroutine2")
        }
        scheduler.advanceTimeBy(500L) //Coorutine1
        scheduler.runCurrent() //Coorutine2


    }

    @Test
    fun `examine advanceTimeBy 3 ()`() {

        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)
        CoroutineScope(dispatcher).launch {
            delay(2)
            println("Coroutine1")
        }
        CoroutineScope(dispatcher).launch {
            delay(4)
            println("Coroutine2")
        }
        CoroutineScope(dispatcher).launch {
            delay(6)
            println("Coroutine3")
        }

        for (i in 1..5) {
            scheduler.advanceTimeBy(1)
            scheduler.runCurrent()
        }

        //Coroutine1
        //Coroutine2
    }

    @Test
    fun `examine advanceTimeBy 4 ()`() {

        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)
        CoroutineScope(dispatcher).launch {
            delay(2)
            println("Coroutine1")
        }
        CoroutineScope(dispatcher).launch {
            delay(4)
            println("Coroutine2")
        }
        CoroutineScope(dispatcher).launch {
            delay(6)
            println("Coroutine3")
        }

        for (i in 1..5) {
            scheduler.advanceTimeBy(1)
            scheduler.runCurrent()
        }
        scheduler.advanceTimeBy(1)
        scheduler.runCurrent()
        //Coroutine1
        //Coroutine2
        //Coroutine3
    }

    @Test
    fun `examine runCurrent()`() {

        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)
        CoroutineScope(dispatcher).launch {
            println("some work 1 -1")
            delay(1000)
            println("some work 2 -1")
            delay(1000)
            print("coroutine 1 done")
        }


        scheduler.runCurrent()

        CoroutineScope(dispatcher).launch {
            delay(500)
            println("some work 2 -2")
        }
        println("Guess What ")


        //some work 1 -1
        //Guess What
    }

    @Test
    fun `examine runCurrent2()`() {

        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)
        CoroutineScope(dispatcher).launch {
            println("some work 1 -1")
            delay(1000)
            println("some work 2 -1")
            delay(1000)
            print("coroutine 1 done")
        }


        scheduler.runCurrent() // some work 1 -1
        scheduler.advanceTimeBy(1000L)
        scheduler.runCurrent() // some work 2 -1
        scheduler.advanceTimeBy(1000L)
        scheduler.runCurrent() // coroutine 1 done


        CoroutineScope(dispatcher).launch {
            delay(500)
            println("some work 2 -1")
        }
        scheduler.advanceTimeBy(500L)
        scheduler.runCurrent() // some work 2 -1
        println("Guess What ")

    }
}
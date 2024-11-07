package com.mohaberabi.androidtestingdeepdive.coroutines.hot_flow

import app.cash.turbine.test
import com.mohaberabi.androidtestingdeepdive.shouldBeEqualTo
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class HotFlowPlayGround {


    @Test

    fun `state  flow simple test `() = runTest {
        val flow = flowOf(1, 2, 3, 4, 5, 6).stateIn(this)
        flow.test {
            6 shouldBeEqualTo awaitItem()
        }
    }

    @Test

    fun `shared   flow simple test `() = runTest {
        val flow = MutableSharedFlow<Int>(
            replay = 3,
        )
        flow.emit(1)
        flow.emit(2)
        flow.emit(3)
        flow.test {
            1 shouldBeEqualTo awaitItem()
            2 shouldBeEqualTo awaitItem()
            3 shouldBeEqualTo awaitItem()

        }
    }

    @Test
    fun `shared  flow simple test 2`() = runTest {
        val flow = MutableSharedFlow<Int>(
            replay = 2,
        )
        flow.emit(1)
        flow.emit(2)
        flow.emit(3)
        flow.test {
            2 shouldBeEqualTo awaitItem()
            3 shouldBeEqualTo awaitItem()

        }
    }

    @Test
    fun `shared  flow simple test 3`() = runTest {
        val flow = MutableSharedFlow<Int>()

        val job = launch(start = CoroutineStart.LAZY) {
            flow.emit(1)
            flow.emit(2)
            flow.emit(3)
        }
        flow.test {
            job.start()
            1 shouldBeEqualTo awaitItem()
            2 shouldBeEqualTo awaitItem()
            3 shouldBeEqualTo awaitItem()
        }
    }

    @Test
    fun `sharedFlow sharing while subscribed`() = runTest {
        val flow = flowOf(
            "event1",
            "event2",
            "event3"
        )
        val sharedFlow = flow
            .onCompletion { println("Completed") }
            .shareIn(
                this,
                SharingStarted.WhileSubscribed(),
                1
            )
        sharedFlow.test {
            awaitItem() shouldBeEqualTo "event1"
            awaitItem() shouldBeEqualTo "event2"
            awaitItem() shouldBeEqualTo "event3"
        }
        coroutineContext.cancelChildren()
    }

    @Test
    fun `sharedFlow sharing lazily`() = runTest {
        val flow = flowOf(
            "event1",
            "event2",
            "event3"
        )
        val sharedFlow = flow
            .onCompletion { println("Completed") }
            .shareIn(
                this,
                SharingStarted.Lazily,
                1
            )
        sharedFlow.test {
            awaitItem() shouldBeEqualTo "event1"
            awaitItem() shouldBeEqualTo "event2"
            awaitItem() shouldBeEqualTo "event3"
        }
        coroutineContext.cancelChildren()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `sharedFlow sharing eagerly`() = runTest(UnconfinedTestDispatcher()) {
        val flow = flowOf(
            "event1",
            "event2",
            "event3"
        )
        val sharedFlow = flow
            .onCompletion { println("Completed") }
            .shareIn(
                this,
                SharingStarted.Eagerly,
                1
            )
        sharedFlow.test {

            awaitItem() shouldBeEqualTo "event3"
            coroutineContext.cancelChildren()
        }
    }
}
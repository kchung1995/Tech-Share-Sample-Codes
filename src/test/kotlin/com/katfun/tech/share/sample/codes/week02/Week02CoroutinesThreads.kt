package com.katfun.tech.share.sample.codes.week02

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class Week02CoroutinesThreads {
    @Test
    fun testMain() = runTest {
        val multiThreadDispatcher = newFixedThreadPoolContext(nThreads = 1, name = "multiThreadDispatcher")

        launch(multiThreadDispatcher) { dailyRoutine("katfun") }
        launch(multiThreadDispatcher) { dailyRoutine("vsfe") }
    }

    suspend fun dailyRoutine(text: String) {
        val delayTime = (3..5).random()
        delay(delayTime * 1000L)
//        Thread.sleep(delayTime * 1000L)

        println("Daily routine of $text took $delayTime seconds.")
    }
}
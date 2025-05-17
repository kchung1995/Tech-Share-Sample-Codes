package com.katfun.tech.share.sample.codes.week06

import com.katfun.tech.share.sample.codes.week06.Week06TestUtils.calledMethod
import com.katfun.tech.share.sample.codes.week06.Week06TestUtils.dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ItemServiceTestExtraTrial {
    @DisplayName("번외 - runTest를 try-catch로 감싸 보자")
    @Test
    fun `wrap the whole scope`() {
        try {
            runTest {
                val list = (0..5).map { it }
                CoroutineScope(dispatcher).launch {
                    list.forEach { num ->
                        launch {    //  여기를 coroutineScope -> launch로 변경
                            delay((1000L..2000L).random())
                            calledMethod(num)
                        }
                    }
                }
                Thread.sleep(10000L)    // 코루틴 결과를 보기 위한 스레드 슬립
            }
        } catch (e: IllegalArgumentException) {
            println("illegal argument exception: ${e.message}")
        } catch (e: Exception) {
            println("exception: ${e.message}")
        }
    }
}




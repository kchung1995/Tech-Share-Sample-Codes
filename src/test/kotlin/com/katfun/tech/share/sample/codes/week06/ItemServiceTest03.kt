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

class ItemServiceTest03 {
    @DisplayName("try-catch를 바깥으로 옮겨 봄. 여전히 못 잡음")
    @Test
    fun `moved try-catch to outside - still not catching exception`() = runTest {
        val list = (0..5).map { it }
        try {   // try 블록을 바깥으로 옮기기
            CoroutineScope(dispatcher).launch {
                list.forEach { num ->
                    launch {
                        delay((1000L..2000L).random())
                        calledMethod(num)
                    }
                }
            }
        } catch (e: IllegalArgumentException) {
            println("illegal argument exception: ${e.message}")
        } catch (e: Exception) {
            println("exception: ${e.message}")
        }

        Thread.sleep(10000L)    // 코루틴 결과를 보기 위한 스레드 슬립
    }
}

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

class ItemServiceTest05 {
    @DisplayName("coroutine Scope를 생성하고 이걸 try catch로 감싼다. 현재 함수가 대기해 버린다.")
    @Test
    fun `create a coroutine scope and wrap it with try catch - current function halts`() = runTest {
        val list = (0..5).map { it }

        CoroutineScope(dispatcher).launch {
            try {
                coroutineScope {    // CoroutineScope.launch -> coroutineScope로 변경
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

        }

        Thread.sleep(10000L)    // 코루틴 결과를 보기 위한 스레드 슬립
    }
}


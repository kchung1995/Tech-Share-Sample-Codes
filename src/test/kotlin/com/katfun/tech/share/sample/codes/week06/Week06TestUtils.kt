package com.katfun.tech.share.sample.codes.week06

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object Week06TestUtils {
    val dispatcher: CoroutineDispatcher = Dispatchers.IO

    fun calledMethod(current: Int) {
        if (current == 5) throw IllegalArgumentException("current is 5")
        println("success for $current")
    }
}
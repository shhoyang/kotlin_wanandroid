package com.hao.library.utils

import kotlinx.coroutines.*

/**
 * @author Yang Shihao
 */
object CoroutineUtils {

    fun io(block: suspend CoroutineScope.() -> Unit) {
        GlobalScope.launch(Dispatchers.IO, block = block)
    }

    fun <T> io2main(
        ioBlock: suspend CoroutineScope.() -> T,
        mainBlock: (T) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = io(ioBlock)
            mainBlock(result)
        }
    }

    private suspend fun <T> io(block: suspend CoroutineScope.() -> T): T =
        withContext(Dispatchers.IO, block)

    fun <T> io2main2(
        ioBlock: () -> T,
        mainBlock: (T) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = ioBlock()
            withContext(Dispatchers.Main) {
                mainBlock(result)
            }
        }
    }
}

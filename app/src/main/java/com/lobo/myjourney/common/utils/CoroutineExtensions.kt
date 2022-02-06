package com.lobo.myjourney.common.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

fun <T> bufferedChannel() = Channel<T>(Channel.BUFFERED)

@ExperimentalCoroutinesApi
suspend fun <T> ReceiveChannel<T>.receiveAll(): List<T> {
    if (isClosedForReceive) {
        return emptyList()
    }

    val data = mutableListOf<T>()

    var item: T? = receive()
    while (item != null && !isClosedForReceive) {
        data.add(item)
        item = poll()
    }

    return data
}

fun <T> throttleFirst(
    skipMs: Long = 300L,
    coroutineScope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var throttleJob: Job? = null
    return { param: T ->
        if (throttleJob?.isCompleted != false) {
            throttleJob = coroutineScope.launch {
                destinationFunction(param)
                delay(skipMs)
            }
        }
    }
}

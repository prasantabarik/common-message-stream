package com.tcs.integration.common.messageProvider

interface MessageListener {
    fun receive(payload: Any)
}

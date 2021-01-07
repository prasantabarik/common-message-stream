package com.tcs.integration.common.messageProvider

interface MessageProvider {

    fun sendMessage(payload: String)
    fun subscribeMessage(): String
}
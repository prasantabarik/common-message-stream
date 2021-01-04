package com.tcs.integration.common.messageProvider

interface MessageProvider {

    fun sendMessage(destination: String, payload: String)
}
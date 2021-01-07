package com.tcs.service.edt.service

import com.tcs.integration.common.configuration.MessagingConfiguration
import com.tcs.integration.common.messageProvider.MessageProvider
import org.springframework.stereotype.Service

@Service
class Service(private val messageProvider: MessageProvider) {

    fun publishMessage(msg: String){
        messageProvider.sendMessage(msg)
    }

    fun subscribeMessage():String {
        return messageProvider.subscribeMessage()
//        println("SUBSCRIBED : " + messageProvider.subscribeMessage())
    }
}
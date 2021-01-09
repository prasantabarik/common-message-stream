package com.tcs.service.edt.service

import com.tcs.integration.common.messageProvider.AbstractMessageProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class Service {
    @Value("\${cm.messaging.topic}")
    lateinit var topic: String

    fun publishMessage(serviceCall: AbstractMessageProvider, type: String, msg: Any) {
        serviceCall.sendMessage(topic, msg)
    }

    fun subscribeMessage(serviceCall: AbstractMessageProvider, type: String): String? {
        return serviceCall.subscribeMessage()
    }
}

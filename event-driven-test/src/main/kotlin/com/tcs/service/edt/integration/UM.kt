package com.tcs.service.edt.integration

import com.tcs.integration.common.messageProvider.AbstractMessageProvider
import com.tcs.service.edt.message.Reactor
import com.tcs.service.edt.service.Service
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class UM(@Qualifier("um")  private val um: AbstractMessageProvider, private val service: Service, private val reactor: Reactor) {
    init {
        um.messageListener = this.reactor
    }

    fun publishMessage(type: String, payload: Any) {
        service.publishMessage(um, type, payload)
    }

    fun subscribeMessage(type: String): String? {
        return service.subscribeMessage(um, type)
    }
}
package com.tcs.service.edt.integration

import com.tcs.integration.common.messageProvider.AbstractMessageProvider
import com.tcs.service.edt.message.Reactor
import com.tcs.service.edt.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class Kafka(@Qualifier("kafka") private val kafka: AbstractMessageProvider, private val service: Service, private val reactor: Reactor) {
    init {
        kafka.messageListener = this.reactor
    }

    fun publishMessage(type: String, payload: Any) {
        service.publishMessage(kafka, type, payload)
    }

    fun subscribeMessage(type: String): String? {
        return service.subscribeMessage(kafka, type)
    }
}

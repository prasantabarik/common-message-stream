package com.tcs.service.edt.message

import com.tcs.integration.common.messageProvider.MessageListener
import org.springframework.stereotype.Component

@Component
class Reactor : MessageListener {
    override fun receive(payload: Any) {
        println("CallBack to the caller method $payload")
    }
}
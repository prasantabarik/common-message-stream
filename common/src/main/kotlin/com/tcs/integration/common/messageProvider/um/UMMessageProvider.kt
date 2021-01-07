package com.tcs.integration.common.messageProvider.um

import com.tcs.integration.common.configuration.ConfigProperties
import com.tcs.integration.common.messageProvider.MessageProvider

class UMMessageProvider (private val configProperties: ConfigProperties): MessageProvider {
    override fun sendMessage(payload: String) {
        Config(configProperties).publish(payload)
    }

    override fun subscribeMessage(): String {
        return Config(configProperties).subscribeMessage()
    }
}

package com.tcs.integration.common.messageProvider.um

import com.tcs.integration.common.configuration.ConfigProperties
import com.tcs.integration.common.messageProvider.MessageProvider

class UMMessageProvider (
        private val configProperties: ConfigProperties
): MessageProvider {
    override fun sendMessage(destination: String, payload: String) {
        Config(configProperties).publish("TEST UM MESSAGE")
    }
}
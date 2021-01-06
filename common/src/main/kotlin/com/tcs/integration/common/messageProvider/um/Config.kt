package com.tcs.integration.common.messageProvider.um

import com.pcbsys.nirvana.client.*
import com.tcs.integration.common.configuration.ConfigProperties

class Config(private val configProperties: ConfigProperties): nEventListener {
    var session: nSession?  = null
    var channel : nChannel? = null

    init {
        val nsa = nSessionAttributes(arrayOf(configProperties.serverUrl))
        session = nSessionFactory.create(nsa)
        session?.init()
        // session.

        val channelAttribute = nChannelAttributes()
        channelAttribute.setName(configProperties.topic)
        channel = session?.findChannel(channelAttribute)
        channel?.addSubscriber(this, 0)

        //channel?.use()
    }

    fun publish(msg: String) {
        val props: nEventProperties = nEventProperties()
        props.put("data", msg)
        channel?.publish(nConsumeEvent("atag", props, "data".toByteArray()))
    }

    override fun go(event: nConsumeEvent) {
        try {
            println("Consumed event " + event.fullChannelName)
            println("data retrieved::" + String(event.eventData))
        } catch (e: nBaseClientException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        println(event.properties)
    }
}

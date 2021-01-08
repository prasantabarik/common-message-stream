package com.tcs.integration.common.messageProvider.um

import com.pcbsys.nirvana.client.*
import com.tcs.integration.common.configuration.ConfigProperties
import com.tcs.integration.common.messageProvider.MessageProvider
import java.util.concurrent.CopyOnWriteArrayList

class UMMessageProvider (private val configProperties: ConfigProperties): nEventListener, MessageProvider {
    private val messages: CopyOnWriteArrayList<String> = CopyOnWriteArrayList<String>()
    var session: nSession?  = null

    fun getChannel(): nChannel? {
        val nsa = nSessionAttributes(arrayOf(configProperties.serverUrl))
        session = nSessionFactory.create(nsa)
        session?.init()

        val channelAttribute = nChannelAttributes()
        channelAttribute.setName(configProperties.topic)
        val channel: nChannel? = session?.findChannel(channelAttribute)
        channel?.addSubscriber(this, 0)

        return channel
    }

    override fun sendMessage(payload: String) {
        println("Published Message:: " + payload)
        val props: nEventProperties = nEventProperties()
        props.put("data", payload)
        getChannel()?.publish(nConsumeEvent("atag", props, "data".toByteArray()))?.use{}
    }

    override fun go(event: nConsumeEvent) {
        try {
            messages.add(event.properties.getString(String(event.eventData)))
        } catch (e: nBaseClientException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    override fun subscribeMessage(): String {
        val result = messages.toString()
        println("SUBSCRIBED MESSAGE:: " + result)
        messages.clear()

        return result
    }

    private fun Any.use(function: () -> Unit) {
        try {
        } finally {
            session?.close()
        }
    }
}

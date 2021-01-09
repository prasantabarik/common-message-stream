package com.tcs.integration.common.messageProvider.um

import com.pcbsys.nirvana.client.*
import com.tcs.integration.common.configuration.ConfigProperties
import com.tcs.integration.common.messageProvider.AbstractMessageProvider
import org.springframework.stereotype.Component
import java.util.concurrent.CopyOnWriteArrayList

@Component
class UMMessageProvider (private val configProperties: ConfigProperties): AbstractMessageProvider(), nEventListener {
    private val messages: CopyOnWriteArrayList<String> = CopyOnWriteArrayList<String>()
    var session: nSession?  = null
    var channel: nChannel?  = null

    fun getSessionValue(): nSession? {
        if (session == null) {
            val nsa = nSessionAttributes(arrayOf(configProperties.serverUMUrl))
            session = nSessionFactory.create(nsa)
            session?.init()
        }

        return session
    }

    override fun sendMessage(destination: String, payload: Any) {
        if (channel == null) {
            val channelAttribute = nChannelAttributes()
            channelAttribute.setName(configProperties.topic)
            channel = getSessionValue()?.findChannel(channelAttribute)
            channel?.addSubscriber(this, 0)
        }

        val props = nEventProperties()
        props.put("data", payload as String)
        channel?.publish(nConsumeEvent("atag", props, "data".toByteArray()))?.use{}
    }

    override fun go(event: nConsumeEvent) {
        try {
            this.messageListener?.receive(event.properties.getString(String(event.eventData)))
            messages.add(event.properties.getString(String(event.eventData)))
            // Not required if topic is created via WM
            // getChannels()?.purgeEvents(event.getEventID(), event.getEventID())
        } catch (e: nBaseClientException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    override fun receive(payload: Any) { }

    override fun subscribeMessage(): String {
        val result = messages.toString()
        messages.clear()

        return result
    }

    private fun Any.use(function: () -> Unit) {
        try {
        } finally {
            session?.close()
            channel = null
            session = null
        }
    }
}

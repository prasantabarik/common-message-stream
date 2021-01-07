package com.tcs.integration.common.messageProvider.um

import com.pcbsys.nirvana.client.*
import com.tcs.integration.common.configuration.ConfigProperties
import java.util.concurrent.CopyOnWriteArrayList

class Config(private val configProperties: ConfigProperties): nEventListener {

    var session: nSession?  = null
    private val messages: CopyOnWriteArrayList<String> = CopyOnWriteArrayList<String>()

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

    fun publish(msg: String) {
        val props: nEventProperties = nEventProperties()
        props.put("data", msg)
        getChannel()?.publish(nConsumeEvent("atag", props, "data".toByteArray()))?.use{}
    }

    override fun go(event: nConsumeEvent) {
        try {
            println("Consumed event " + event.fullChannelName)
            println("data retrieved::" + String(event.eventData))
            messages.add(String(event.eventData))
        } catch (e: nBaseClientException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        println(event.properties)
    }

    fun subscribeMessage(): String {
        val result = messages.toString()
        println(result)
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

package com.tcs.integration.common.messageProvider.kafka

import com.tcs.integration.common.configuration.ConfigProperties
import com.tcs.integration.common.messageProvider.MessageProvider
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import java.io.Closeable
import java.util.concurrent.CopyOnWriteArrayList

@EnableKafka
class KafkaMessageProvider(private val configProperties: ConfigProperties) : MessageProvider, Closeable {
    private val messages: CopyOnWriteArrayList<String> = CopyOnWriteArrayList<String>()
    var producerFactory: DefaultKafkaProducerFactory<String, Any>? = null

    private fun producerFactory(): ProducerFactory<String, Any> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = configProperties.serverUrl
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        producerFactory = DefaultKafkaProducerFactory(configProps)
        // producerFactory.isProducerPerThread = true
        return producerFactory as DefaultKafkaProducerFactory<String, Any>
    }

    fun kafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory())
    }

    @KafkaListener(topics = ["StoreOrderReference"], groupId = "kafka-subscribe")
    fun receive(payload: String) {
        messages.add(payload)
        println("KAFKA MESSAGE RECEIVED :: " + payload)
    }

    override fun subscribeMessage(): String {
        val result = messages.toString()
        messages.clear()
        return result
    }

    override fun sendMessage(payload: String) {
        println("Message - ${payload}")
        kafkaTemplate().send(configProperties.topic, payload).use{}
    }

    override fun close() {
        println("Finalize Close calls here")
    }

    private fun Any.use(function: () -> Unit) {
        try {
        } finally {
            producerFactory?.destroy()
        }
    }
}

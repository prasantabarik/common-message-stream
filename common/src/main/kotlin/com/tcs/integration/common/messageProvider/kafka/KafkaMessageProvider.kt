package com.tcs.integration.common.messageProvider.kafka

import com.tcs.integration.common.configuration.ConfigProperties
import com.tcs.integration.common.messageProvider.AbstractMessageProvider
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.*
import java.io.Closeable

@EnableKafka
class KafkaMessageProvider(private val configProperties: ConfigProperties) : AbstractMessageProvider(), Closeable {
    private var producerFactory: DefaultKafkaProducerFactory<String, Any>? = null

    private fun producerFactory(): ProducerFactory<String, Any> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = configProperties.serverKafkaUrl
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        producerFactory = DefaultKafkaProducerFactory(configProps)
        // producerFactory.isProducerPerThread = true
        return producerFactory as DefaultKafkaProducerFactory<String, Any>
    }

    fun kafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory())
    }

    override fun receive(payload: Any) { }

    override fun subscribeMessage(): String {
        return ""
    }

    override fun sendMessage(destination: String, payload: Any) {
        kafkaTemplate().send(destination, payload).use{}
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

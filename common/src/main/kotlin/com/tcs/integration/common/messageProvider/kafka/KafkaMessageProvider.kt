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
import org.springframework.kafka.support.TransactionSupport


import java.time.Duration


@EnableKafka
class KafkaMessageProvider(
        private val configProperties: ConfigProperties
) : MessageProvider {
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
        println("KAFKA MESSAGE RECEIVED :: " + payload)
    }

    override fun sendMessage(destination: String, payload: String) {
        println("SENDMESSAGE :: TOPIC - ${destination} Message - ${payload}")
        kafkaTemplate().send(destination,payload).use{}
    }

    private fun Any.use(function: () -> Unit) {
        try {
            // println("COMES INTO USE TRY LISTENER")
        } finally {
            producerFactory?.destroy()
        }
    }
}


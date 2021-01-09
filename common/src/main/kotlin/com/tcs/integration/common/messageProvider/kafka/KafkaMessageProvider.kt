package com.tcs.integration.common.messageProvider.kafka

import com.tcs.integration.common.configuration.ConfigProperties
import com.tcs.integration.common.messageProvider.AbstractMessageProvider
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.stereotype.Component
import java.io.Closeable
import java.util.concurrent.CopyOnWriteArrayList

@EnableKafka
@Component
class KafkaMessageProvider(private val configProperties: ConfigProperties) : AbstractMessageProvider(), Closeable {
    private val messages: CopyOnWriteArrayList<String> = CopyOnWriteArrayList<String>()
    var producerFactory: DefaultKafkaProducerFactory<String, Any>? = null

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

    @KafkaListener(topics = ["StoreOrderReference"], groupId = "kafka-subscribe")
    override fun receive(payload: Any) {
        println("KAFKA MESSAGE RECEIVED :: $payload")
        val record: ConsumerRecord<String, Any> = payload as ConsumerRecord<String, Any>
        this.messageListener?.receive(record.value())
        messages.add(record.value() as String?)
    }

    override fun subscribeMessage(): String {
        val result = messages.toString()
        messages.clear()
        return result
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

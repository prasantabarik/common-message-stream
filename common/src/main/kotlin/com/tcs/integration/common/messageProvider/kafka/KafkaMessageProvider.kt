package com.tcs.integration.common.messageProvider.kafka

import com.tcs.integration.common.configuration.ConfigProperties
import com.tcs.integration.common.messageProvider.MessageProvider
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.stereotype.Component

@Configuration
@EnableKafka
class KafkaMessageProvider(
        private val configProperties: ConfigProperties
) : MessageProvider {
    @Bean
    private fun producerFactory(): ProducerFactory<String, String> {
        println("serverUrl $configProperties.serverUrl")
        val configProps: MutableMap<String, Any> = HashMap()
        //configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = configProperties.serverUrl
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    private fun kafkaTemplate(): KafkaTemplate<String, String> {
        println("COMES INTO KAFKATEMPLATE")
        return KafkaTemplate(producerFactory())
    }
    override fun sendMessage(destination: String, payload: String) {
        kafkaTemplate().send(destination,payload)
    }
}
//package com.tcs.integration.common.messageProvider.kafka
//
//import com.tcs.integration.common.configuration.ConfigProperties
//import com.tcs.integration.common.messageProvider.AbstractMessageProvider
//import org.apache.kafka.clients.consumer.ConsumerConfig
////import org.apache.kafka.clients.consumer.ConsumerRecord
//import org.apache.kafka.common.serialization.StringDeserializer
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.kafka.annotation.EnableKafka
////import org.springframework.kafka.annotation.KafkaListener
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
//import org.springframework.kafka.core.*
//import org.springframework.stereotype.Component
////import org.springframework.stereotype.Component
//import java.io.Closeable
//import java.util.concurrent.CopyOnWriteArrayList
//
//@EnableKafka
//@Component
//class KafkaSubscriber(private val configProperties: ConfigProperties) : AbstractMessageProvider(), Closeable {
//    private val messages: CopyOnWriteArrayList<String> = CopyOnWriteArrayList<String>()
//    var consumerFactory: DefaultKafkaConsumerFactory<String, Any>? = null
//    var listenerFactory: ConcurrentKafkaListenerContainerFactory<String, Any>? = null
//
//    private fun consumerFactory(): ConsumerFactory<String, Any> {
//        val configProps: MutableMap<String, Any> = HashMap()
//        configProps[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = configProperties.serverKafkaUrl
//        configProps[ConsumerConfig.GROUP_ID_CONFIG] = "kafka-subscribe";
//        configProps[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
//        configProps[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
//        consumerFactory = DefaultKafkaConsumerFactory(configProps)
//        return consumerFactory as DefaultKafkaConsumerFactory<String, Any>
//    }
//
//    @Bean
//    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any>? {
//        listenerFactory = ConcurrentKafkaListenerContainerFactory<String, Any>()
//        listenerFactory!!.setConsumerFactory(consumerFactory())
//        return listenerFactory
//    }
//
////    @KafkaListener(topics = ["com.tcs.service.edt.model.PrepareECMR", "com.tcs.service.model.PostECMR",
////        "com.tcs.service.edt.model.ECMRPosted"], groupId = "kafka-subscribe")
//    override fun receive(payload: Any) {
////        println("KAFKA MESSAGE RECEIVED SUBSCRIBER :: $payload")
////        val record: ConsumerRecord<String, Any> = payload as ConsumerRecord<String, Any>
////        this.messageListener!!.receive("kafka", record.value())
////        messages.add(record.value() as String?)
//    }
//
//    override fun subscribeMessage(): String {
//        val result = messages.toString()
//        messages.clear()
//        return ""
//    }
//
//    override fun sendMessage(destination: String, payload: Any) {}
//
//    override fun close() {
//        println("Finalize Close calls here")
//    }
//}
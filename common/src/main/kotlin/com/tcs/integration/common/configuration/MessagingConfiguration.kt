package com.tcs.integration.common.configuration

import com.tcs.integration.common.messageProvider.MessageProvider
import com.tcs.integration.common.messageProvider.kafka.KafkaMessageProvider
import com.tcs.integration.common.messageProvider.um.UMMessageProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.PreDestroy
import kotlin.jvm.Throws

@Configuration
class MessagingConfiguration(private val configProperties: ConfigProperties) {

    @Bean
    @ConditionalOnProperty(name = ["cm.messaging.provider.type"], havingValue = "kafka")
    fun messageProviderKafka(): MessageProvider {
        return KafkaMessageProvider(configProperties)
    }

    @Bean
    @ConditionalOnProperty(name = ["cm.messaging.provider.type"], havingValue = "um")
     fun messageProviderUM(): MessageProvider {
        return UMMessageProvider(configProperties)
    }

    @PreDestroy
    @Throws(Exception::class)
    fun onDestroy() {
        println("Spring Container is destroyed!")
    }
}
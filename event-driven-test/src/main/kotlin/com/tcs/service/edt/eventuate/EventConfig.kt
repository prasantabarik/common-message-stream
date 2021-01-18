package com.tcs.service.edt.eventuate

import io.eventuate.tram.consumer.common.NoopDuplicateMessageDetector
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration
import io.eventuate.tram.spring.events.publisher.TramEventsPublisherConfiguration
import io.eventuate.tram.spring.events.subscriber.TramEventSubscriberConfiguration
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@Import(EventuateTramKafkaMessageConsumerConfiguration::class, TramEventSubscriberConfiguration::class,
        TramEventsPublisherConfiguration::class, TramMessageProducerJdbcConfiguration::class,
        NoopDuplicateMessageDetector::class)
@EnableJpaRepositories
class EventConfiguration {
//    @Bean
//    fun EventConsumer(): Consumer? {
//        return Consumer()
//    }
//
//    @Bean
//    fun domainEventDispatcher(consumer: Consumer, domainEventDispatcherFactory: DomainEventDispatcherFactory): DomainEventDispatcher? {
//        return domainEventDispatcherFactory.make("EventPostECMR", consumer.domainEventHandlers())
//    }
}

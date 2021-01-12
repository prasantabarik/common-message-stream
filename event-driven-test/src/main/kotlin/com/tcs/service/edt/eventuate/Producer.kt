package com.tcs.service.edt.eventuate

import com.tcs.service.edt.model.PreECMR
import com.tcs.service.edt.model.PrepareECMR
import io.eventuate.tram.events.common.DomainEvent
import io.eventuate.tram.events.publisher.DomainEventPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class Producer() {
    @Autowired
    lateinit var  domainEventPublisher: DomainEventPublisher

    fun create(createPreECMR : PrepareECMR) {
        publishTodoEvent(createPreECMR, PreECMR(createPreECMR.shipment_id))
    }

    fun publishTodoEvent(preECMR: PrepareECMR, vararg domainEvents: DomainEvent) {
        domainEventPublisher.publish(PrepareECMR::class.java, preECMR.shipment_id, listOf(*domainEvents))
    }
}

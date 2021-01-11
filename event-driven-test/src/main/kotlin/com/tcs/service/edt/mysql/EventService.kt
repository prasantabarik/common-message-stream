package com.tcs.service.edt.mysql

import com.tcs.service.edt.model.Events
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Service

@Service
//@EnableJpaRepositories
//@EntityScan(basePackageClasses = arrayOf(Events::class))
class EventService {
    @Autowired
    lateinit var eventRepo: EventRepository

    fun create(event: Events) {
        eventRepo?.save(event)
    }
}
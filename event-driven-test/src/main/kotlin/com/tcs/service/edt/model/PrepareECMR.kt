package com.tcs.service.edt.model

import io.eventuate.tram.events.common.DomainEvent
import javax.persistence.*

//@Entity
//@Table(name="events")
class PrepareECMR(message: String) {
    var shipment_id: String = ""

    init {
        this.shipment_id = message
    }
}

package com.tcs.service.edt.model

import io.eventuate.tram.events.common.DomainEvent

class PreECMR(message: String) : DomainEvent {
    var shipment_id: String = ""

    init {
        this.shipment_id = message
    }
}

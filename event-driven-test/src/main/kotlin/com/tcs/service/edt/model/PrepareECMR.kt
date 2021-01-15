package com.tcs.service.edt.model

class PrepareECMR(message: String) {
    var shipment_id: String = ""

    init {
        this.shipment_id = message
    }
}

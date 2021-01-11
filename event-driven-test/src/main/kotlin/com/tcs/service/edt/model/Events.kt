package com.tcs.service.edt.model

import javax.persistence.*

@Entity
@Table(name="events")
data class Events (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var event_id: Long,
    var event_type: String,
    var event_data: String,
    var entity_type: String,
    var entity_id: String,
    var triggering_event: String,
    var metadata: String,
    var published: Boolean
)

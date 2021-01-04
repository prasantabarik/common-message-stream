package com.tcs.service.edt.controller.v1

import com.tcs.service.edt.service.Service
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(private val service: Service) {

    @RequestMapping(value = ["/api/postEvents/{msg}"], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMessage(@PathVariable msg: String){
        service.publishMessage(msg)
    }
}
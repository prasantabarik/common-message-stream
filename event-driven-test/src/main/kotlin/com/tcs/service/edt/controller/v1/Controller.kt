package com.tcs.service.edt.controller.v1

import com.tcs.service.edt.service.Service
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class Controller(private val service: Service) {

    @RequestMapping(value = ["/api/postEvents"], method = [RequestMethod.POST])
//            , produces=[MediaType.APPLICATION_JSON_VALUE])
    fun getMessage(@RequestBody payload: String){
        service.publishMessage(payload)
    }
}
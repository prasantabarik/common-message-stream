package com.tcs.service.edt.controller.v1

import com.tcs.service.edt.service.Service
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.concurrent.schedule

@RestController
class Controller(private val service: Service) {
    @RequestMapping(value = ["/api/postEvents"], method = [RequestMethod.POST])
    fun getMessage(@RequestBody payload: String){
        service.publishMessage(payload)
    }

    @RequestMapping(value = ["/api/getEvents"], method = [RequestMethod.GET])
    @ResponseBody
    fun getMessage(): String {
        println("Called Subscriber")
        return service.subscribeMessage()
    }
}

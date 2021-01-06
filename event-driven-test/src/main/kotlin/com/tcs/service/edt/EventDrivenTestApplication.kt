package com.tcs.service.edt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.tcs.service.edt", "com.tcs.integration.common"])
class EventDrivenTestApplication

fun main(args: Array<String>) {
	runApplication<EventDrivenTestApplication>(*args)
}

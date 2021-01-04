package com.tcs.service.edt

import com.tcs.integration.common.messageProvider.MessageProvider
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.core.KafkaTemplate


@SpringBootApplication(scanBasePackages = ["com.tcs.service.edt", "com.tcs.integration.common"])
class EventDrivenTestApplication

fun main(args: Array<String>) {
	val context = runApplication<EventDrivenTestApplication>(*args)
	val kafkaTemplate: KafkaTemplate<String, Any> = context.getBean(KafkaTemplate::class.java) as KafkaTemplate<String, Any>
//	val testObject = object {
//		var data: String = "Publish StoreorderRef"
//	}
//
//	kafkaTemplate.send(Config.properties!!["topic"].toString(), testObject)
//	println("KAFKA MESSAGE PUBLISHED")
//	println("COMES HERE BEAN LOADED")
//	println(context.getBean(MessageProvider::class.java))
}

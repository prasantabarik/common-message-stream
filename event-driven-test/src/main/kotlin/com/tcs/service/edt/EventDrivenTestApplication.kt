package com.tcs.service.edt

import RxBus
import org.json.JSONObject
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import java.sql.Timestamp
import java.util.*

import com.tcs.service.edt.eventuate.Producer
import com.tcs.service.edt.model.PrepareECMR

@SpringBootApplication(scanBasePackages = ["com.tcs.service.edt", "com.tcs.integration.common"])

class EventDrivenTestApplication

fun main(args: Array<String>) {
	val ctx: ConfigurableApplicationContext = runApplication<EventDrivenTestApplication>(*args)
	// Listen for String events only
	RxBus.listen(JSONObject::class.java).subscribe {
		try {
			when (it.optString("type")) {
				"um" -> {
					val today = Date()
					val ts1 = Timestamp(today.time)
					val obj = JSONObject(it.optString("data"))
					ctx.getBean(Producer::class.java).create(PrepareECMR(obj.getString("shipmentId")))

//					khttp.post(
//							url  = "http://localhost:8099/api/postEvents/kafka",
//							json = mapOf("data" to it.optString("data")))

					/*
					Even though just leave it for future reference
					 */
					/*
					val events: Events = Events(
						// event_id is dummy
						ts1.getTime(),
						"publish", it.optString("data"),
						"publish", "1", "test",
						"test-meta", false
					)
					ctx.getBean(EventService::class.java).create(events)
					 */
				}
			}
		} catch(e: Exception) {
			println(e)
		}
	}
//	ctx.getBean(MessagingConfiguration::class.java)

	// ctx.close()
	//	val exitCode = SpringApplication.exit(ctx, ExitCodeGenerator { // return the error code
	//		0
	//	})
	//System.exit(exitCode)
}


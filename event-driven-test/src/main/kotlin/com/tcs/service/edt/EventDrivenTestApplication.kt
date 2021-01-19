package com.tcs.service.edt

import RxBus
import com.tcs.service.edt.eventuate.Producer
import com.tcs.service.edt.model.PrepareECMR
import khttp.post
import org.json.JSONObject
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

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
				"kafka" -> {
					val obj = JSONObject(JSONObject(it.optString("data")).optString("headers"))
					val regex = "\\.([A-Za-z]+)$".toRegex()
					val matchResult = regex.find(obj.getString("event-aggregate-type"))
					val (eventName) = matchResult!!.destructured
					val url = ctx.environment.getProperty("cm.int.elastic-search.url") + obj.getString("PARTITION_ID") + "-" + eventName
					println("Elastic search URLs :: $url")
					try {
						val df = SimpleDateFormat("EE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
						df.timeZone = TimeZone.getTimeZone("GMT")
						val date = df.parse(obj.getString("DATE"))
						val newDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
						val requestObject = mapOf(
								"@timestamp" to newDate.format(df.parse(obj.getString("DATE"))),
								"event_type" to eventName,
								"shipment_id" to obj.getString("PARTITION_ID"),
								"message" to it.optString("data")
						)
						val response = post(
								url = url,
								headers = mapOf("Content-Type" to "application/json"),
								json = requestObject)
//						println(response.text)
//						println(response.content)
					} catch(e: Exception) {
						println("Elastic search Exception :: $e")
					}
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

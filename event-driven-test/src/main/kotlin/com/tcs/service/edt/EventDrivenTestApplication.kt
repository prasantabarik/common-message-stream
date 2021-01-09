package com.tcs.service.edt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication(scanBasePackages = ["com.tcs.service.edt", "com.tcs.integration.common"])
class EventDrivenTestApplication

fun main(args: Array<String>) {
	val ctx: ConfigurableApplicationContext = runApplication<EventDrivenTestApplication>(*args)
//	ctx.getBean(MessagingConfiguration::class.java)

	// ctx.close()
	//	val exitCode = SpringApplication.exit(ctx, ExitCodeGenerator { // return the error code
	//		0
	//	})
	//System.exit(exitCode)
}

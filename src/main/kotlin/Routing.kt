package com.reed

import com.reed.config.ServiceInfoProvider
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }

    val serviceInfoProvider = ServiceInfoProvider(this)

    routing {
        get("/") {
            // Common "service info" payload for easy debugging / uptime checks.
            call.respond(serviceInfoProvider.snapshot(status = "OK"))
        }
        get("/status") {
            // Same payload shape as `/` so consumers can rely on a stable contract.
            call.respond(serviceInfoProvider.snapshot(status = "OK"))
        }
    }
}

package com.reed.config

import com.reed.models.ServiceInfoResponse
import io.ktor.server.application.*
import java.net.InetAddress
import java.time.Instant

/**
 * Central place to build the payload returned by `/` and `/status`.
 *
 * Values are sourced from (in order):
 * 1) application.yaml (`service.*`)
 * 2) environment variables
 * 3) safe defaults
 */
class ServiceInfoProvider(private val application: Application) {

    private val startedAtMs: Long = System.currentTimeMillis()

    private fun configString(path: String): String? =
        application.environment.config.propertyOrNull(path)?.getString()?.takeIf { it.isNotBlank() }

    private fun envString(name: String): String? =
        System.getenv(name)?.takeIf { it.isNotBlank() }

    fun snapshot(status: String = "OK"): ServiceInfoResponse {
        val name = configString("service.name")
            ?: envString("SERVICE_NAME")
            ?: "reed-backend"

        val version = configString("service.version")
            ?: envString("SERVICE_VERSION")
            ?: "0.0.0"

        val environment = configString("service.environment")
            ?: envString("SERVICE_ENV")
            ?: "unknown"

        val host = try {
            InetAddress.getLocalHost().hostName
        } catch (_: Exception) {
            null
        }

        return ServiceInfoResponse(
            name = name,
            version = version,
            environment = environment,
            status = status,
            serverTimeIso = Instant.now().toString(),
            uptimeMs = System.currentTimeMillis() - startedAtMs,
            host = host
        )
    }
}

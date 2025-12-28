package com.reed

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }

        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(headers[HttpHeaders.ContentType]?.contains(ContentType.Application.Json.toString()) == true)

            val body = bodyAsText()
            // Lightweight assertions to avoid coupling tests to timestamps/uptime.
            assertTrue(body.contains("\"name\""))
            assertTrue(body.contains("\"version\""))
            assertTrue(body.contains("\"environment\""))
            assertTrue(body.contains("\"status\""))
            assertTrue(body.contains("\"serverTimeIso\""))
            assertTrue(body.contains("\"uptimeMs\""))
        }
    }
}

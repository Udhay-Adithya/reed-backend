package com.reed.models

import kotlinx.serialization.Serializable

@Serializable
data class ServiceInfoResponse(
    val name: String,
    val version: String,
    val environment: String,
    val status: String,
    val serverTimeIso: String,
    val uptimeMs: Long,
    val host: String? = null
)


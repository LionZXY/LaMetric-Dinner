package uk.kulikov.model

import kotlinx.serialization.Serializable

@Serializable
data class LaMetricHost(
    val host: String,
    val token: String
)
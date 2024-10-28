package uk.kulikov.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ApplicationConfig(
    val hosts: List<LaMetricHost>,
    @SerialName("slack_webhook_url")
    val slackWebhookUrl: String
)

@Serializable
data class LaMetricHost(
    val host: String,
    val token: String
)
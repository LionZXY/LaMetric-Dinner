package uk.kulikov.model.api

import kotlinx.serialization.Serializable

@Serializable
data class SlackMessage(
    val text: String
)
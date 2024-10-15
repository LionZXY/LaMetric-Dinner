package uk.kulikov.model.api

import kotlinx.serialization.Serializable

@Serializable
data class LaMetricResponse(
    val frames: List<LaMetricFrame>
)

@Serializable
data class LaMetricFrame(
    val icon: String,
    val text: String
)
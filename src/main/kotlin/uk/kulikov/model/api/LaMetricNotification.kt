package uk.kulikov.model.api

import kotlinx.serialization.Serializable

@Serializable
data class LaMetricNotification(
    val priority: String = "info",
    val model: LaMetricNotificationModel
)

@Serializable
data class LaMetricNotificationModel(
    val cycles: Int = 1,
    val frames: List<LaMetricFrame>,
    val sound: LaMetricSound? = null
)

@Serializable
data class LaMetricSound(
    val category: String,
    val id: String
)
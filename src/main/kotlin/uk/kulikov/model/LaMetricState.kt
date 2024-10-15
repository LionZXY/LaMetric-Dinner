package uk.kulikov.model

import uk.kulikov.model.api.*

enum class LaMetricState(
    val icon: Int,
    val soundId: String?,
    val text: String
) {
    NO_ACTIONS(
        icon = 5018,
        soundId = null,
        text = "-"
    ),
    FOOD_ORDER(
        icon = 21380,
        soundId = "notification",
        text = "CHOOSE"
    ),
    FOOD_DELIVERY(
        icon = 22835,
        soundId = null,
        text = "COMING"
    ),
    FOOD_READY(
        icon = 44692,
        soundId = "energy",
        text = "EAT!"
    ),
    HENRY(
        icon = 18816,
        soundId = "notification",
        text = "ENGLISH"
    )
}

fun LaMetricState.toResponse() = LaMetricResponse(
    frames = listOf(
        LaMetricFrame(
            icon = icon.toString(),
            text = text
        )
    )
)

fun LaMetricState.toNotification() = LaMetricNotification(
    model = LaMetricNotificationModel(
        frames = listOf(
            LaMetricFrame(
                icon = icon.toString(),
                text = text
            )
        ),
        sound = if (soundId != null) {
            LaMetricSound(
                category = "notifications",
                id = soundId
            )
        } else null
    )
)
package uk.kulikov.utils

import io.ktor.client.request.*
import io.ktor.http.*
import uk.kulikov.model.LaMetricState
import uk.kulikov.model.api.SlackMessage

object SlackNotifier {
    suspend fun notify(state: LaMetricState) {
        val message = when (state) {
            LaMetricState.NO_ACTIONS -> return
            LaMetricState.FOOD_ORDER -> ":k_popcorn: It's time to *order food*! Check out the links in #office-london"
            LaMetricState.FOOD_DELIVERY -> ":k_crazy: Order placed, awaiting *delivery*"
            LaMetricState.FOOD_READY -> ":k_yahoo: The food's here! :k_yahoo:"
            LaMetricState.HENRY -> ":k_king: It's English time! Henry is here"
        }

        val response = httpClient.post(ApplicationConfigHelper.config.slackWebhookUrl) {
            setBody(SlackMessage(message))
            contentType(ContentType.Application.Json)
        }
    }
}
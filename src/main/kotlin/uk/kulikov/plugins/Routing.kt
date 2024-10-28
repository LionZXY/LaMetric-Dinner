package uk.kulikov.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import uk.kulikov.model.LaMetricState
import uk.kulikov.model.StateHolder
import uk.kulikov.model.toResponse
import uk.kulikov.utils.LaMetricNotifier
import uk.kulikov.utils.SlackNotifier
import java.io.File
import javax.swing.plaf.nimbus.State

fun Application.configureRouting() {
    routing {
        get("/change") {
            val status = call.request.queryParameters["state"]?.toIntOrNull() ?: 0
            val state = LaMetricState.entries[status]
            StateHolder.state = state
            LaMetricNotifier.notify(state)
            SlackNotifier.notify(state)

            call.respond("Change to $state")
        }
        get("/state") {
            call.respond(StateHolder.state.toResponse())
        }
        staticFiles("/", File("static/index.html"))
    }
}

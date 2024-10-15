package uk.kulikov.model

import kotlinx.datetime.*

private val DEFAULT = LaMetricState.NO_ACTIONS
private val TIMEZONE = TimeZone.of("Europe/London")

object StateHolder {
    private var stateWithTimezone = now() to LaMetricState.NO_ACTIONS

    var state: LaMetricState
        get() {
            return if (stateWithTimezone.first != now()) {
                DEFAULT
            } else stateWithTimezone.second
        }
        set(value) {
            stateWithTimezone = now() to value
        }

    private fun now(): LocalDate {
        return Clock.System.todayIn(TIMEZONE)
    }
}
package uk.kulikov.model

import kotlinx.datetime.*
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.io.decodeFromSource
import kotlinx.serialization.json.io.encodeToSink

private val DEFAULT = LaMetricState.NO_ACTIONS
private val TIMEZONE = TimeZone.of("Europe/London")
private val storageFile = Path("data/data.json")

@Serializable
private data class SavedState(
    val time: LocalDate = now(),
    val state: LaMetricState = LaMetricState.NO_ACTIONS,
)

object StateHolder {
    @OptIn(ExperimentalSerializationApi::class)
    var state: LaMetricState
        get() {
            val stateWithTimezone = runCatching {
                SystemFileSystem.source(storageFile).buffered().use {
                    Json.decodeFromSource<SavedState>(it)
                }
            }.getOrNull() ?: SavedState()

            return if (stateWithTimezone.time != now()) {
                DEFAULT
            } else stateWithTimezone.state
        }
        set(value) {
            SystemFileSystem.sink(storageFile).buffered().use { sink ->
                Json.encodeToSink(
                    SavedState(
                        state = value
                    ),
                    sink
                )
            }
        }

}

private fun now(): LocalDate {
    return Clock.System.todayIn(TIMEZONE)
}
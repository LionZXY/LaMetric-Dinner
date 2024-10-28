package uk.kulikov.utils

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.io.decodeFromSource
import uk.kulikov.model.ApplicationConfig

private const val CONFIG_PATH = "data/config.json"

object ApplicationConfigHelper {
    @OptIn(ExperimentalSerializationApi::class)
    val config by lazy {
        SystemFileSystem.source(Path(CONFIG_PATH)).use {
            Json.decodeFromSource<ApplicationConfig>(it.buffered())
        }
    }
}
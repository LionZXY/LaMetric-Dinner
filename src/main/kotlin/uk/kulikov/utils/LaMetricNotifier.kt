package uk.kulikov.utils

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.io.buffered
import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.io.decodeFromSource
import org.slf4j.LoggerFactory
import uk.kulikov.model.LaMetricHost
import uk.kulikov.model.LaMetricState
import uk.kulikov.model.toNotification
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

object LaMetricNotifier {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { explicitNulls = false })
        }
        install(Logging)
        engine {
            https {
                trustManager = object : X509TrustManager {
                    override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {}

                    override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {}

                    override fun getAcceptedIssuers(): Array<X509Certificate>? = null
                }
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val hosts = SystemFileSystem.source(Path("hosts.json")).use {
        Json.decodeFromSource<List<LaMetricHost>>(it.buffered())
    }


    suspend fun notify(state: LaMetricState) {
        hosts.forEach { host ->
            try {
                notifyOneDevice(host, state)
            } catch (e: Throwable) {
                logger.error("Failed to notify $state to $host", e)
            }
        }
    }

    private suspend fun notifyOneDevice(host: LaMetricHost, state: LaMetricState) {
        val notification = state.toNotification()
        val response = client.post("${host.host}/api/v2/device/notifications") {
            setBody(notification)
            basicAuth("dev", host.token)
            contentType(ContentType.Application.Json)
        }


        logger.info(response.toString())
    }
}
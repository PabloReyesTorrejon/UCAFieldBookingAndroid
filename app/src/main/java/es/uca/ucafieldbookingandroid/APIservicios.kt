package es.uca.ucafieldbookingandroid

import Reserva
import android.widget.DatePicker
import android.widget.TimePicker
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import io.ktor.http.Parameters
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.*

class APIservicios {
    private val client = HttpClient(Android)
    private val IP_PORT = "10.0.2.2:3000"

    suspend fun createReserva(nombre: String, idsocio: Int, deporte: String, email: String, fecha: String, hora: String, asistentes: Int, comentario: String): HttpResponse {

        return client.submitForm(
            url = "http://"+IP_PORT+"/reservas/annadir_reserva",
            formParameters = Parameters.build {
                append("nombre", nombre)
                append("idsocio", idsocio.toString())
                append("deporte", deporte)
                append("email", email)
                append("fecha", fecha)
                append("hora", hora)
                append("asistentes", asistentes.toString())
                append("comentario", comentario)
            },
            encodeInQuery = false
        )
    }

    suspend fun getReservas(idsocio: String): List<Reserva> {
        val response: HttpResponse = client.get("http://"+IP_PORT+"/reservas/get_reserva/$idsocio")
        val content: String = response.bodyAsText()
        return Json { ignoreUnknownKeys = true }.decodeFromString<List<Reserva>>(content)
    }

    suspend fun editarReserva(nombre: String, deporte: String, email: String, fecha: String, hora: String, asistentes: String, comentario: String): HttpResponse {
        return client.submitForm(
            url = "http://"+IP_PORT+"/reservas/modificar_reserva",
            formParameters = Parameters.build {
                append("nombre", nombre)
                append("deporte", deporte)
                append("email", email)
                append("fecha", fecha)
                append("hora", hora)
                append("asistentes", asistentes.toString())
                append("comentario", comentario)
            },
            encodeInQuery = false
        )
    }

}

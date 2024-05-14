@file:OptIn(InternalAPI::class)

package es.uca.ucafieldbookingandroid

import Reserva
import android.content.Context
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.delete
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context


class APIservicios(private val context: Context) {
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
        val result = Json { ignoreUnknownKeys = true }.decodeFromString<List<Reserva>>(content)
        val resultString = Json.encodeToString(ListSerializer(Reserva.serializer()), result)
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("lastResult", resultString)
        editor.apply()
        return Json { ignoreUnknownKeys = true }.decodeFromString<List<Reserva>>(content)
    }

    suspend fun editarReserva(
        idsocio: String,
        nombre: String? = null,
        deporte: String? = null,
        email: String? = null,
        fecha: String,
        hora: String,
        asistentes: String? = null,
        comentario: String? = null
    ): HttpResponse {
        val params = buildJsonObject {
            nombre?.let { put("nombre", JsonPrimitive(it)) }
            deporte?.let { put("deporte", JsonPrimitive(it)) }
            email?.let { put("email", JsonPrimitive(it)) }
            put("fecha", JsonPrimitive(fecha))
            put("hora", JsonPrimitive(hora))
            asistentes?.let { put("asistentes", JsonPrimitive(it)) }
            comentario?.let { put("comentario", JsonPrimitive(it)) }
        }

        return client.put("http://$IP_PORT/reservas/modificar_reserva/$idsocio") {
            contentType(ContentType.Application.Json)
            body = params.toString()
        }
    }

    suspend fun eliminarReserva(idsocio: String): HttpResponse {
        return client.delete("http://$IP_PORT/reservas/delete_reserva/$idsocio")
    }
}

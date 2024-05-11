package es.uca.ucafieldbookingandroid

import android.widget.DatePicker
import android.widget.TimePicker
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters
import java.text.SimpleDateFormat
import java.util.*

class APIservicios {
    private val client = HttpClient(Android)
    private val IP_PORT = "10.0.2.2:3000"

    // suspend indica que esta función es una función de suspensión, lo que
    // significa que puede hacer llamadas de red u otras operaciones de E/S
    // de manera asíncrona sin bloquear el subproceso principal.
    suspend fun getSaludo(name: String): HttpResponse {
        return client.get("http://10.0.2.2:3000/saludo/$name")
    }

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

    suspend fun getReservas(idsocio: Number): HttpResponse {
        // Obtiene la respuesta HTTP como antes
        val response = client.get("http://"+IP_PORT+"/reservas/get_reserva/$idsocio")
        return response
    }

}

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

    // suspend indica que esta función es una función de suspensión, lo que
    // significa que puede hacer llamadas de red u otras operaciones de E/S
    // de manera asíncrona sin bloquear el subproceso principal.
    suspend fun getSaludo(name: String): HttpResponse {
        return client.get("http://10.0.2.2:3000/saludo/$name")
    }

    suspend fun createReserva(nombre: String, idsocio: Int, email: String, fecha: String, hora: String, asistentes: Int, comentario: String): HttpResponse {

        return client.submitForm(
            url = "http://10.0.2.2:3000/reservas/annadir_reserva",
            formParameters = Parameters.build {
                append("idsocio", idsocio.toString())
                append("nombre", nombre)
                append("email", email)
                append("fecha", fecha)
                append("hora", hora)
                append("asistentes", asistentes.toString())
                append("comentario", comentario)
            },
            encodeInQuery = false
        )
    }

    suspend fun getReservas(idsocio: String): HttpResponse {
        // Obtiene la respuesta HTTP como antes
        val response = client.get("http://10.0.2.2:3000/reservas/$idsocio")
        return response
    }

}

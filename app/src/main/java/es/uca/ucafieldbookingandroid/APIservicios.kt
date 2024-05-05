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

    suspend fun createReserva(nombre: String, idsocio: Int, email: String, fecha: DatePicker, hora: TimePicker, asistentes: Int): HttpResponse {

        // Obtener fecha y hora seleccionadas por el usuario
        val selectedDate = Calendar.getInstance()
        selectedDate.set(fecha.year, fecha.month, fecha.dayOfMonth)
        val selectedTime = Calendar.getInstance()
        selectedTime.set(Calendar.HOUR_OF_DAY, hora.hour)
        selectedTime.set(Calendar.MINUTE, hora.minute)

        // Formatear la fecha y hora seleccionadas como cadenas de texto
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val fechaFormateada = dateFormat.format(selectedDate.time)
        val horaFormateada = timeFormat.format(selectedTime.time)

        return client.submitForm(
            url = "http://10.0.2.2:3000/reservas/annadir_reserva",
            formParameters = Parameters.build {
                append("idsocio", idsocio.toString())
                append("nombre", nombre)
                append("email", email)
                append("fecha", fechaFormateada)
                append("hora", horaFormateada)
                append("asistentes", asistentes.toString())
            },
            encodeInQuery = false
        )
    }

    suspend fun getPersona(id: String): HttpResponse {
        // Obtiene la respuesta HTTP como antes
        val response = client.get("http://10.0.2.2:3000/persona/$id")
        return response
    }

}

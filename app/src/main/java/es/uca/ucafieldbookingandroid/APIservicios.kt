package es.uca.ucafieldbookingandroid

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters

class APIservicios {
    private val client = HttpClient(Android)

    // suspend indica que esta función es una función de suspensión, lo que
    // significa que puede hacer llamadas de red u otras operaciones de E/S
    // de manera asíncrona sin bloquear el subproceso principal.
    suspend fun getSaludo(name: String): HttpResponse {
        return client.get("http://10.0.2.2:3000/saludo/$name")
    }

    suspend fun createPersona(id: Int, nombre: String, apellido: String, edad: Int): HttpResponse {
        return client.submitForm(
            url = "http://10.0.2.2:3000/persona",
            formParameters = Parameters.build {
                append("id", id.toString())
                append("nombre", nombre)
                append("apellido", apellido)
                append("edad", edad.toString())
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

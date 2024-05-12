package es.uca.ucafieldbookingandroid

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditarReserva : AppCompatActivity() {
    private val apiServicios = APIservicios()
    // Almacena los valores iniciales de los campos
    private lateinit var nombreInicial: String
    private lateinit var deporteInicial: String
    private lateinit var emailInicial: String
    private lateinit var asistentesInicial: String
    private lateinit var comentarioInicial: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val idsocio = intent.getStringExtra("IDSOCIO_EXTRA")
        setContentView(R.layout.activity_editar_reserva)
        val buttonPut = findViewById<Button>(R.id.buttonPut)
        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextDeporte = findViewById<EditText>(R.id.editTextDeporte)
        val editTextemail = findViewById<EditText>(R.id.editTextEmail)
        val dpFecha = findViewById<DatePicker>(R.id.dpFecha)
        val tpHora = findViewById<TimePicker>(R.id.tpFecha)
        val editTextAsistentes = findViewById<EditText>(R.id.editTextAsistentes)
        val editTextComentario = findViewById<EditText>(R.id.editTextComentario)

        // Guarda los valores iniciales de los campos
        nombreInicial = editTextNombre.text.toString()
        deporteInicial = editTextDeporte.text.toString()
        emailInicial = editTextemail.text.toString()
        asistentesInicial = editTextAsistentes.text.toString()
        comentarioInicial = editTextComentario.text.toString()

        var idCounter = 1

        buttonPut.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val deporte = editTextDeporte.text.toString()
            val email = editTextemail.text.toString()
            val asistentes = editTextAsistentes.text.toString()
            val comentario = editTextComentario.text.toString()

            // Obtener fecha y hora seleccionadas por el usuario
            val selectedDate = Calendar.getInstance()
            selectedDate.set(dpFecha.year, dpFecha.month, dpFecha.dayOfMonth)
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY, tpHora.hour)
            selectedTime.set(Calendar.MINUTE, tpHora.minute)

            // Formatear la fecha y hora seleccionadas como cadenas de texto
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val fechaFormateada = dateFormat.format(selectedDate.time)
            val horaFormateada = timeFormat.format(selectedTime.time)

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = apiServicios.editarReserva(
                        idsocio?:"",
                        if (nombre != nombreInicial) nombre else null,
                        if (deporte != deporteInicial) deporte else null,
                        if (email != emailInicial) email else null,
                        fechaFormateada,
                        horaFormateada,
                        if (asistentes != asistentesInicial) asistentes else null,
                        if (comentario != comentarioInicial) comentario else null
                    )
                    val responseText = response.bodyAsText() // Extraer el texto de la respuesta

                    val intent = Intent(this@EditarReserva, Reservas::class.java)
                    intent.putExtra("snackbar_message", responseText)
                    startActivity(intent)
                } catch (e: ClientRequestException) {
                    launch(Dispatchers.Main) {
                        Snackbar.make(findViewById(android.R.id.content), "Error: ${e.response?.status?.value} ${e.response?.status?.description} ${e.response?.body<String>()}", Snackbar.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    launch(Dispatchers.Main) {
                        Snackbar.make(findViewById(android.R.id.content), "Error: ${e.message}", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
    fun ocultarTecladoExplicitamente(view: View) {
        val ocultar = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        ocultar?.hideSoftInputFromWindow(view.windowToken, 0) ?: Log.e("MainActivity", "InputMethodManager no disponible")
        runOnUiThread{
            view.clearFocus()
        }
    }
}
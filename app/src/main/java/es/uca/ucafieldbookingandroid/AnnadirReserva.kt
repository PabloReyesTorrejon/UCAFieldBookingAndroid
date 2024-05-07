package es.uca.ucafieldbookingandroid

import android.content.Context
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
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AnnadirReserva : AppCompatActivity() {
    private val apiServicios = APIservicios()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_annadir_reserva)
        val buttonPost = findViewById<Button>(R.id.buttonPost)
        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextIDsocio = findViewById<EditText>(R.id.editTextIDsocio)
        val editTextemail = findViewById<EditText>(R.id.editTextEmail)
        val dpFecha = findViewById<DatePicker>(R.id.dpFecha)
        val tpHora = findViewById<TimePicker>(R.id.tpFecha)
        val editTextAsistentes = findViewById<EditText>(R.id.editTextAsistentes)
        val editTextComentario = findViewById<EditText>(R.id.editTextComentario)
        val textResponse = findViewById<TextView>(R.id.textResponse)


        var idCounter = 1

        buttonPost.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val idsocio = editTextIDsocio.text.toString().toIntOrNull()
            val email = editTextemail.text.toString()
            val asistentes = editTextAsistentes.text.toString().toIntOrNull()
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

            if (nombre.isNotEmpty() && idsocio != null && email.isNotEmpty() && asistentes != null && fechaFormateada.isNotEmpty() && horaFormateada.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val response = apiServicios.createReserva(nombre, idsocio, email, fechaFormateada, horaFormateada, asistentes, comentario)
                        launch(Dispatchers.Main) {
                            //Si queremos ver el texto en pantalla en lugar de con el Snackbar
                            //textResponse.text = response.bodyAsText()

                            //Ejemplo de Snackbar
                            Snackbar.make(it, response.bodyAsText(), Snackbar.LENGTH_INDEFINITE)
                                .setAction("OK") { /* No se requiere ninguna acción obligatoria */ }
                                .show()
                        }

                        editTextNombre.setText("")
                        editTextIDsocio.setText("")
                        editTextemail.setText("")
                        editTextAsistentes.setText("")
                        editTextComentario.setText("")


                        // Quitar el enfoque de cualquier vista que lo tenga
                        // Plegar/ocultar el teclado numérico
                        currentFocus?.let { focusedView ->
                            ocultarTecladoExplicitamente(focusedView)
                        }


                    } catch (e: ClientRequestException) {
                        launch(Dispatchers.Main) {
                            mostrarToast("Error: ${e.response.status.description}")
                        }
                    } catch (e: Exception) {
                        launch(Dispatchers.Main) {
                            mostrarToast("Error: ${e.message}")
                        }
                    }
                }
            } else {
                mostrarToast("Por favor, completa todos los campos correctamente")
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
    fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
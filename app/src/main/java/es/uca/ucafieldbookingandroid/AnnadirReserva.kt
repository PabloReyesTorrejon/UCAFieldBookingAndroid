package es.uca.ucafieldbookingandroid

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AnnadirReserva : AppCompatActivity() {

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
        val textEditAsistentes = findViewById<EditText>(R.id.editTextAsistentes)




    }
}
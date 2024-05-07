package es.uca.ucafieldbookingandroid

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Reservas : AppCompatActivity() {
    private val apiServicios = APIservicios()
    private val reservas = mutableListOf<Reserva>()
    private lateinit var adapter: ReservaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservas)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //enableEdgeToEdge()
        //setContentView(R.layout.activity_reservas)

        val editTextUserId = findViewById<EditText>(R.id.editTextUserId)
        val buttonGet = findViewById<Button>(R.id.buttonGet)

        // Obtiene una referencia al RecyclerView del layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Crea una instancia del adaptador para la lista de mascotas
        adapter = ReservaAdapter(reservas)
        // Asigna el adaptador al RecyclerView
        recyclerView.adapter = adapter
        // Asigna el LayoutManager al RecyclerView
        // en este caso se usa LinearLayoutManager para mostrar la lista en una sola columna
        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonGet.setOnClickListener {
            val idUsuario = editTextUserId.text.toString()
            if (idUsuario.isNotEmpty()) {
                // Realiza la búsqueda de reservas con el ID de usuario ingresado
                buscarReservas(idUsuario)
            } else {
                // Muestra un mensaje de error al usuario indicando que el campo está vacío
                Toast.makeText(this, "Por favor, introduce un ID de usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_layout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_annadir_pista -> {
                val intent = Intent(this@Reservas, AnnadirReserva::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun buscarReservas(idUsuario: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiServicios.getReservas(idUsuario)
                if (response.status.isSuccess()) {
                    // Procesar la respuesta y llenar la lista de reservas
                    val reservasJsonArray = response.body<List<Reserva>>()
                    reservas.clear() // Limpiar la lista antes de agregar las nuevas reservas
                    reservas.addAll(reservasJsonArray)

                    // Actualizar el RecyclerView con las nuevas reservas
                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    // Procesamiento de la respuesta de error
                    Toast.makeText(this@Reservas, "Usted no ha realizado ninguna reserva", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Manejo de errores
            }
        }
    }



}
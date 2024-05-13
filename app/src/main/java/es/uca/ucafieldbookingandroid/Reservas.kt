package es.uca.ucafieldbookingandroid

import Reserva
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


class Reservas : AppCompatActivity() {
    private val apiServicios = APIservicios()
    private val reservas = mutableListOf<Reserva>()
    private lateinit var adapter: ReservaAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var salas: TextView
    private lateinit var vistaReservas: TextView
    private lateinit var local: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservas)
        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)

        salas = findViewById(R.id.boton_salas)
        vistaReservas = findViewById(R.id.boton_reservas)
        local = findViewById(R.id.boton_local)

        drawerLayout = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val editTextUserId = findViewById<EditText>(R.id.editTextUserId)
        val buttonGet = findViewById<Button>(R.id.buttonGet)

        // Obtiene una referencia al RecyclerView del layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Crea una instancia del adaptador para la lista de mascotas
        adapter = ReservaAdapter(reservas, apiServicios, lifecycleScope)        // Asigna el adaptador al RecyclerView
        recyclerView.adapter = adapter
        // Asigna el LayoutManager al RecyclerView
        // en este caso se usa LinearLayoutManager para mostrar la lista en una sola columna
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Muestra un mensaje de Snackbar si se recibe un mensaje de otra actividad
        val message = intent.getStringExtra("snackbar_message")
        if (message != null) {
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
        }

        buttonGet.setOnClickListener {
            val idUsuario = editTextUserId.text.toString()
            if (idUsuario.isNotEmpty()){
                // Realiza la búsqueda de reservas con el ID de usuario ingresado
                buscarReservas(idUsuario)
            } else {
                // Muestra un mensaje de error al usuario indicando que el campo está vacío
                Toast.makeText(this, "Por favor, introduce un ID de usuario", Toast.LENGTH_SHORT).show()
            }
        }

        salas.setOnClickListener{
            redirectActivity(this@Reservas, Salas::class.java)
        }

        vistaReservas.setOnClickListener{
            redirectActivity(this@Reservas, Reservas::class.java)
        }

        local.setOnClickListener{
            redirectActivity(this@Reservas, Salas::class.java)
        }
    }

    private fun buscarReservas(idUsuario: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                Log.d("Reservas", "Iniciando solicitud de reservas")
                val reservasJsonArray = apiServicios.getReservas(idUsuario)

                // Limpiar la lista antes de agregar las nuevas reservas
                reservas.clear()
                // Agregar cada reserva al adaptador
                reservas.addAll(reservasJsonArray)

                // Actualizar el RecyclerView con las nuevas reservas en el hilo principal
                runOnUiThread {
                    Log.d("Reservas", "Actualizando RecyclerView")
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                // Manejo de errores
                Log.e("Reservas", "Error al obtener reservas: ${e.message}")
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

    fun openDrawer(drawerLayout: DrawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    fun closeDrawer(drawerlayout: DrawerLayout){
        if(drawerlayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    fun redirectActivity(activity: Activity, secondActivity: Class<*>) {
        val intent = Intent(activity, secondActivity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
        /*activity.finish()*/
    }

    override fun onPause() {
        super.onPause()
        closeDrawer(drawerLayout)
    }
}
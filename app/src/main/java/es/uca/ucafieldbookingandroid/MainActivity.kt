package es.uca.ucafieldbookingandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menu: ImageView
    private lateinit var salas: TextView
    private lateinit var reservas: TextView
    private lateinit var local: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Se obtienen las referencias del los botones del Navigation Drawer
        drawerLayout = findViewById(R.id.drawerLayout)
        menu = findViewById(R.id.menu)
        salas = findViewById(R.id.boton_salas)
        reservas = findViewById(R.id.boton_reservas)
        local = findViewById(R.id.boton_local)

        // El botón del actionbar abre el Navigation Drawer
        menu.setOnClickListener {
            openDrawer(drawerLayout)
        }

        // Los botones del Drawer cambian de actividad
        salas.setOnClickListener{
            redirectActivity(this@MainActivity, Salas::class.java)
        }

        reservas.setOnClickListener{
            redirectActivity(this@MainActivity, Reservas::class.java)
        }

        local.setOnClickListener{
            redirectActivity(this@MainActivity, Localizacion::class.java)
        }

    }

    // Función para abrir el Drawer
    fun openDrawer(drawerLayout: DrawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    // Si el drawer está abierto lo cierra
    fun closeDrawer(drawerlayout: DrawerLayout){
        if(drawerlayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    // Abre la nueva actividad
    fun redirectActivity(activity: Activity, secondActivity: Class<*>) {
        val intent = Intent(activity, secondActivity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

    // Si se pulsa fuera del drawer, se cierra
    override fun onPause() {
        super.onPause()
        closeDrawer(drawerLayout)
    }
}
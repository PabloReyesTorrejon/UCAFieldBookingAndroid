package es.uca.ucafieldbookingandroid

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class Localizacion : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menu: ImageView
    private lateinit var salas: TextView
    private lateinit var reservas: TextView
    private lateinit var local: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_localizacion)

        // Se obtienen las referencias del los botones del Navigation Drawer
        drawerLayout = findViewById(R.id.local)
        menu = findViewById(R.id.menu)
        salas = findViewById(R.id.boton_salas)
        reservas = findViewById(R.id.boton_reservas)
        local = findViewById(R.id.boton_local)

        // Referencias de las imagenes de los transportes
        val autobusButton = findViewById<CardView>(R.id.autobuses)
        val trenesButton = findViewById<ImageView>(R.id.trenes)

        // Al pulsar en las imágenes, abre el enlace correspondiente
        autobusButton.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://siu.cmtbc.es/es/movil/index.php")))
        }

        trenesButton.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.renfe.com/es/es/cercanias/cercanias-cadiz")))
        }

        // El botón del actionbar abre el Navigation Drawer
        menu.setOnClickListener {
            openDrawer(drawerLayout)
        }

        // Los botones del Drawer cambian de actividad
        salas.setOnClickListener{
            redirectActivity(this@Localizacion, Salas::class.java)
        }

        reservas.setOnClickListener{
            redirectActivity(this@Localizacion, Reservas::class.java)
        }

        local.setOnClickListener{
            redirectActivity(this@Localizacion, Localizacion::class.java)
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
        activity.finish()
    }

    // Si se pulsa fuera del drawer, se cierra
    override fun onPause() {
        super.onPause()
        closeDrawer(drawerLayout)
    }
}
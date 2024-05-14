package es.uca.ucafieldbookingandroid

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.TypedValue
import android.view.ContentInfo
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout

class Salas : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menu: ImageView
    private lateinit var salas: TextView
    private lateinit var reservas: TextView
    private lateinit var local: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_salas)

        // Se obtienen las referencias a cada parte de las tarjetas
        val cardView1 = findViewById<CardView>(R.id.cardView_expandable1)
        val cardView2 = findViewById<CardView>(R.id.cardView_expandable2)
        val cardView3 = findViewById<CardView>(R.id.cardView_expandable3)
        val fixedView1 = findViewById<RelativeLayout>(R.id.fixed_view1)
        val fixedView2 = findViewById<RelativeLayout>(R.id.fixed_view2)
        val fixedView3 = findViewById<RelativeLayout>(R.id.fixed_view3)
        val expandableView1 = findViewById<RelativeLayout>(R.id.expandable_view1)
        val expandableView2 = findViewById<RelativeLayout>(R.id.expandable_view2)
        val expandableView3 = findViewById<RelativeLayout>(R.id.expandable_view3)

        // Referencia al botón de descarga del pdf
        val descarga = findViewById<Button>(R.id.botonDescarga)

        // Al pulsar en las tarjetas, se expanden
        cardView1.setOnClickListener{
            expandir(cardView1, fixedView1, expandableView1)
        }

        cardView2.setOnClickListener{
            expandir(cardView2, fixedView2, expandableView2)
        }

        cardView3.setOnClickListener{
            expandir(cardView3, fixedView3, expandableView3)
        }

        // Al pulsar en el botón de descarga, se realiza una request a la url en la que está alojado
        // utilizando el DownloadManager. Se muestra un toast para indicar que la descarga ha comenzado
        descarga.setOnClickListener{
            var download= this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            var PdfUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/pdf-downloader-f301f.appspot.com/o/UCA%20Field%20Booking.pdf?alt=media&token=ae62494c-fa57-4eb9-813e-5cc751874b88")
            var getPdf = DownloadManager.Request(PdfUri)
            getPdf.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            download.enqueue(getPdf)
            Toast.makeText(this,"Descargando", Toast.LENGTH_LONG).show()
        }

        // Se obtienen las referencias del los botones del Navigation Drawer
        drawerLayout = findViewById(R.id.salas)
        menu = findViewById(R.id.menu)
        salas = findViewById(R.id.boton_salas)
        reservas = findViewById(R.id.boton_reservas)
        local = findViewById(R.id.boton_local)

        // El botón de la toolbar abre el Navigation Drawer
        menu.setOnClickListener {
            openDrawer(drawerLayout)
        }

        // Los botones del Drawer cambian de actividad
        salas.setOnClickListener{
            recreate()
        }

        reservas.setOnClickListener{
            redirectActivity(this@Salas, Reservas::class.java)
        }

        local.setOnClickListener{
            redirectActivity(this@Salas, Localizacion::class.java)
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

    // Recibe un cardView y sus RelativeLayout fijo y oculto
    fun expandir(cardView:CardView, fixedView: RelativeLayout, expandableView: RelativeLayout){
        if (expandableView.visibility == View.GONE){ // Si la parte extensible está oculta
            TransitionManager.beginDelayedTransition(cardView, AutoTransition())
            expandableView.visibility = View.VISIBLE // Lo hace visible con una transición
            fixedView.layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, resources.displayMetrics).toInt() // Encoge la parte fija
        }
        else{ // Si está visible
            TransitionManager.beginDelayedTransition(cardView, AutoTransition())
            expandableView.visibility = View.GONE // Se oculta con una transición
            fixedView.layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, resources.displayMetrics).toInt() // La parte fija vuelve a su tamaño normal
        }
    }
}
package es.uca.ucafieldbookingandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        val botonExpandir = findViewById<Button>(R.id.boton_expand)
        val expandableView = findViewById<RelativeLayout>(R.id.expandable_view)
        val cardView = findViewById<CardView>(R.id.cardView_expandable)

        botonExpandir.setOnClickListener{
            Log.d("BUTTONS", "User tapped the Supabutton")
            Toast.makeText(botonExpandir.context, "Boton", Toast.LENGTH_SHORT).show()
            if (expandableView.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                expandableView.visibility = View.VISIBLE
            }
            else{
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                expandableView.visibility = View.GONE
            }
        }

        drawerLayout = findViewById(R.id.salas)
        menu = findViewById(R.id.menu)
        salas = findViewById(R.id.boton_salas)
        reservas = findViewById(R.id.boton_reservas)
        local = findViewById(R.id.boton_local)

        menu.setOnClickListener {
            openDrawer(drawerLayout)
        }

        salas.setOnClickListener{
            redirectActivity(this@Salas, Salas::class.java)
        }

        reservas.setOnClickListener{
            redirectActivity(this@Salas, AnnadirReserva::class.java)
        }

        local.setOnClickListener{
            redirectActivity(this@Salas, Salas::class.java)
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
        activity.finish()
    }

    override fun onPause() {
        super.onPause()
        closeDrawer(drawerLayout)
    }
}
package es.uca.ucafieldbookingandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.TypedValue
import android.view.ContentInfo
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
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

        /*val botonExpandir = findViewById<Button>(R.id.boton_expand)*/
        val expandableView = findViewById<RelativeLayout>(R.id.expandable_view)
        val fixedView = findViewById<RelativeLayout>(R.id.fixed_view)
        val cardView = findViewById<CardView>(R.id.cardView_expandable)
        val imageView = findViewById<ImageView>(R.id.imageView2)

        cardView.setOnClickListener{
            /*Log.d("BUTTONS", "User tapped the Supabutton")
            Toast.makeText(carView.context, "Boton", Toast.LENGTH_SHORT).show()*/
            if (expandableView.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                expandableView.visibility = View.VISIBLE
                fixedView.layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80f, resources.displayMetrics).toInt()
            }
            else{
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                expandableView.visibility = View.GONE
                fixedView.layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, resources.displayMetrics).toInt()
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
        /*activity.finish()*/
    }

    override fun onPause() {
        super.onPause()
        closeDrawer(drawerLayout)
    }
}
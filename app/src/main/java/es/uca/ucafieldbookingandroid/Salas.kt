package es.uca.ucafieldbookingandroid

import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Salas : AppCompatActivity() {
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
                TransitionManager.beginDelayedTransition(cardView)
                expandableView.visibility = View.VISIBLE
            }
            else{
                TransitionManager.beginDelayedTransition(cardView)
                expandableView.visibility = View.GONE
            }
        }
    }
}
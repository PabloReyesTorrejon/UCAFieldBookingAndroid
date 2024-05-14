package es.uca.ucafieldbookingandroid
import Reserva
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import es.uca.ucafieldbookingandroid.R
import kotlinx.serialization.json.Json

class IdSocioWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        // Recuperar el último resultado de getReservas
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val lastResultString = sharedPreferences.getString("lastResult", "[]") // "[]" es el valor por defecto
        val lastResult = Json.decodeFromString<List<Reserva>>(lastResultString!!)

        // Convertir cada reserva en una cadena con un formato más legible y unir todas las cadenas con un separador
        val reservasString = lastResult.joinToString("\n\n") { reserva ->
            "Nombre: ${reserva.nombre}\nFecha: ${reserva.fecha}\nHora: ${reserva.hora}"
        }

        // Construir la vista del widget
        val views = RemoteViews(context.packageName, R.layout.id_socio_widget)
        views.setTextViewText(R.id.id_socio_text, reservasString)

        // Crear un Intent para iniciar la actividad Reservas
        val intent = Intent(context, Reservas::class.java)
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Asignar el PendingIntent al widget
        views.setOnClickPendingIntent(R.id.id_socio_logo, pendingIntent)

        // Actualizar el widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}
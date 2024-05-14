package es.uca.ucafieldbookingandroid
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import es.uca.ucafieldbookingandroid.R

class IdSocioWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // Hay un widget para cada elemento en appWidgetIds
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        // Recuperar el último idSocio registrado
        // Esto es solo un ejemplo, necesitarás implementar la lógica para recuperar el último idSocio registrado
        val lastIdSocio = "1234"

        // Construir la vista del widget
        val views = RemoteViews(context.packageName, R.layout.id_socio_widget)
        views.setTextViewText(R.id.id_socio_text, "Último ID Socio: $lastIdSocio")

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
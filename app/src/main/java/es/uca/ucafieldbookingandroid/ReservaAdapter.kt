package es.uca.ucafieldbookingandroid

import Reserva
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

// Define la clase ReservaAdapter y extiende la clase RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder>. Esta clase maneja el
//conjunto de datos de Reserva y lo une con la vista que se va a mostrar en la lista.
class ReservaAdapter(private val reservas: List<Reserva>) :
// Se define la clase interna ReservaViewHolder, que extiende de la clase RecyclerView.ViewHolder.
RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder>() {
    // Esta clase almacena las referencias de los elementos de la vista (los widgets o views) que se muestran en cada elemento de la
    // lista.
    inner class ReservaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.textViewReservaNombre)
        private var idsocio: String? = null
        private val deporteTextView: TextView = itemView.findViewById(R.id.textViewReservaDeporte)
        private val verPistaButton: Button = itemView.findViewById(R.id.buttonVerReserva)
        private val editarReservaButton: Button = itemView.findViewById(R.id.buttonEditarReserva)
        private val eliminarReservaButton: Button = itemView.findViewById(R.id.buttonEliminarReserva)
        private val textViewExtra1: TextView = itemView.findViewById(R.id.textViewReservaFecha)
        private val textViewExtra2: TextView = itemView.findViewById(R.id.textViewReservaHora)
        private val textViewExtra3: TextView = itemView.findViewById(R.id.textViewReservaAsistentes)
        private val textViewExtra4: TextView = itemView.findViewById(R.id.textViewReservaComentario)
        private val detailsLayout: LinearLayout = itemView.findViewById(R.id.linearLayoutDetails)

        init {
            // Agrega un OnClickListener al botón "Ver reserva"
            verPistaButton.setOnClickListener {
                // Cambia la visibilidad de los TextView adicionales a View.VISIBLE cuando no estan visibles. Cuando lo estén, los oculta
                detailsLayout.visibility = if (detailsLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE

            }
            // Agrega un OnClickListener al botón "Editar reserva"
            editarReservaButton.setOnClickListener {
                val context = it.context
                val intent = Intent(context, EditarReserva::class.java)
                intent.putExtra("IDSOCIO_EXTRA", idsocio)
                startActivity(context, intent, null)
            }
        }

        fun bind(reserva: Reserva) {
            nombreTextView.text = reserva.nombre
            idsocio = reserva._id
            deporteTextView.text = "Deporte: " + reserva.deporte
            textViewExtra1.text = "Fecha: " + reserva.fecha
            textViewExtra2.text = "Hora: " + reserva.hora
            textViewExtra3.text = "Asistentes: " + reserva.asistentes
            textViewExtra4.text = "Comentario: " + reserva.comentario
            // Puedes configurar la visibilidad inicial de los TextView adicionales según sea necesario
            detailsLayout.visibility = View.GONE // Hacer invisible por defecto


        }
    }
    // El método onCreateViewHolder() se encarga de crear una nueva instancia de ReservaViewHolder, inflando el diseño de vista desde
    // el archivo de diseño XML que se proporciona en el parámetro viewType.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_reservas, parent, false)
        return ReservaViewHolder(itemView)
    }
    // El método onBindViewHolder() se llama para establecer los datos del objeto Reserva en la vista ReservaViewHolder.
    override fun onBindViewHolder(holder: ReservaAdapter.ReservaViewHolder, position: Int) {
        // Obtiene la Reserva en la posición actual
        val reserva = reservas[position]
        holder.bind(reserva)
    }
    // El método getItemCount() devuelve el número de elementos en la lista de Reservas proporcionado en el constructor de
    // ReservaAdapter.
    override fun getItemCount() = reservas.size
}
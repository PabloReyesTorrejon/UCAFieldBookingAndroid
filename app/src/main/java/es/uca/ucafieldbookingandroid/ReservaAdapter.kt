package es.uca.ucafieldbookingandroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
        private val deporteTextView: TextView = itemView.findViewById(R.id.textViewReservaDeporte)
        private val verPistaButton: Button = itemView.findViewById(R.id.buttonVerReserva)
        private val textViewExtra1: TextView = itemView.findViewById(R.id.textViewReservaFecha)
        private val textViewExtra2: TextView = itemView.findViewById(R.id.textViewReservaHora)
        private val textViewExtra3: TextView = itemView.findViewById(R.id.textViewReservaAsistentes)
        private val textViewExtra4: TextView = itemView.findViewById(R.id.textViewReservaComentario)


        init {
            // Agrega un OnClickListener al botón "Ver reserva"
            verPistaButton.setOnClickListener {
                // Cambia la visibilidad de los TextView adicionales a View.VISIBLE
                textViewExtra1.visibility = View.VISIBLE
                textViewExtra2.visibility = View.VISIBLE
                textViewExtra3.visibility = View.VISIBLE
                textViewExtra4.visibility = View.VISIBLE

            }
        }

        fun bind(reserva: Reserva) {
            nombreTextView.text = reserva.nombre
            deporteTextView.text = reserva.deporte
            // Puedes configurar la visibilidad inicial de los TextView adicionales según sea necesario
            textViewExtra1.visibility = View.GONE
            textViewExtra2.visibility = View.GONE
            textViewExtra3.visibility = View.GONE
            textViewExtra4.visibility = View.GONE

        }
    }
    // El método onCreateViewHolder() se encarga de crear una nueva instancia de ReservaViewHolder, inflando el diseño de vista desde
    // el archivo de diseño XML que se proporciona en el parámetro viewType.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_reservas, parent, false)
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
import kotlinx.serialization.Serializable

@Serializable
data class Reserva(
    val _id: String,
    val nombre: String,
    val idsocio: String,
    val deporte: String,
    val email: String,
    val fecha: String,
    val hora: String,
    val asistentes: String,
    val comentario: String
)
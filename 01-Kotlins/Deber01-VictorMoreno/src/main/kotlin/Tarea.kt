import java.util.Date

data class Tarea(
    val id: Int,
    var descripcion: String,
    var fechaVencimiento: Date,
    var estaCompletada: Boolean,
    var prioridad: Int
)

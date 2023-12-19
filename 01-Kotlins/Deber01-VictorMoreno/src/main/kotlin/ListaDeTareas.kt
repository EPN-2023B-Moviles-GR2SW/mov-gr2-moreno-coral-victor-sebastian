import java.util.Date

data class ListaDeTareas(
    val id: Int,
    var nombre: String,
    val fechaCreacion: Date,
    val tareas: MutableList<Tarea> = mutableListOf()
)

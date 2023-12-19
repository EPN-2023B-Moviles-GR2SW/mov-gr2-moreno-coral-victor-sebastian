import java.util.*

interface AlmacenamientoDeDatos {
    fun guardarDatos(datos: List<ListaDeTareas>)
    fun cargarDatos(): List<ListaDeTareas>
}

class ControladorListaDeTareas(private val almacenamientoDeDatos: AlmacenamientoDeDatos) {
    private val listasDeTareas = almacenamientoDeDatos.cargarDatos().toMutableList()

    fun crearListaDeTareas(listaDeTareas: ListaDeTareas) {
        listasDeTareas.add(listaDeTareas)
        guardarDatos()
    }

    fun editarListaDeTareas(idListaDeTareas: Int, nuevoNombre: String) {
        val lista = listasDeTareas.find { it.id == idListaDeTareas }
        lista?.nombre = nuevoNombre
        guardarDatos()
    }

    fun eliminarListaDeTareas(idListaDeTareas: Int) {
        listasDeTareas.removeIf { it.id == idListaDeTareas }
        guardarDatos()
    }

    fun crearTareaEnLista(idListaDeTareas: Int, tarea: Tarea) {
        val lista = listasDeTareas.find { it.id == idListaDeTareas }
        lista?.tareas?.add(tarea)
        guardarDatos()
    }

    fun mostrarListasDeTareas() {
        listasDeTareas.forEach { listaDeTareas ->
            println("Lista de Tareas: ${listaDeTareas.nombre}")
            listaDeTareas.tareas.forEach { tarea ->
                println(" - Tarea: ${tarea.descripcion}, Fecha de Vencimiento: ${tarea.fechaVencimiento}, Completada: ${tarea.estaCompletada}")
            }
        }
    }
    fun obtenerNuevaId(): Int {
        return if (listasDeTareas.isEmpty()) {
            1
        } else {
            // Obtén el ID más alto y suma 1 para obtener un nuevo ID único.
            listasDeTareas.maxByOrNull { it.id }!!.id + 1
        }
    }
    fun obtenerListaPorNumero(numeroLista: Int): ListaDeTareas? {
        return listasDeTareas.firstOrNull { it.id == numeroLista }
    }
    private fun guardarDatos() {
        almacenamientoDeDatos.guardarDatos(listasDeTareas)
    }
    fun eliminarTarea(idLista: Int, idTarea: Int) {
        val lista = listasDeTareas.find { it.id == idLista }
        val tarea = lista?.tareas?.find { it.id == idTarea }

        if (tarea != null) {
            lista.tareas.remove(tarea)
            guardarDatos()
            println("Tarea eliminada con éxito.")
        } else {
            println("La tarea con ID $idTarea no existe en la lista con ID $idLista.")
        }
    }
    fun editarTarea(
        idLista: Int,
        idTarea: Int,
        nuevaDescripcion: String,
        nuevaFechaVencimiento: Date,
        nuevaEstaCompletada: Boolean,
        nuevaPrioridad: Int
    ) {
        val lista = listasDeTareas.find { it.id == idLista }
        val tarea = lista?.tareas?.find { it.id == idTarea }

        if (tarea != null) {
            tarea.apply {
                descripcion = nuevaDescripcion
                fechaVencimiento = nuevaFechaVencimiento
                estaCompletada = nuevaEstaCompletada
                prioridad = nuevaPrioridad
            }

            guardarDatos()
        } else {
            println("La tarea con ID $idTarea no existe en la lista con ID $idLista.")
        }
    }
}

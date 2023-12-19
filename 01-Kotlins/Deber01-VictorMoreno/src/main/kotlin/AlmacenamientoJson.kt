import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class AlmacenamientoJson(private val gson: Gson) : AlmacenamientoDeDatos {
    private val nombreArchivo = "tareas.json"

    override fun guardarDatos(datos: List<ListaDeTareas>) {
        val json = gson.toJson(datos)
        File(nombreArchivo).writeText(json)
    }

    override fun cargarDatos(): List<ListaDeTareas> {
        if (File(nombreArchivo).exists()) {
            val json = File(nombreArchivo).readText()
            val tipoLista = object : TypeToken<List<ListaDeTareas>>() {}.type
            return gson.fromJson(json, tipoLista)
        }
        return emptyList()
    }
}

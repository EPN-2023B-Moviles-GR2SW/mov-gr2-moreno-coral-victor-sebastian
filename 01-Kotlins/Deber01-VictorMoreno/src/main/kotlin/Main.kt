import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val gson = Gson()
    val almacenamientoJson = AlmacenamientoJson(gson)
    val controladorListaDeTareas = ControladorListaDeTareas(almacenamientoJson)
    val scanner = Scanner(System.`in`)

    while (true) {
        println("---- Menú ----")
        println("1. Crear Lista de Tareas")
        println("2. Mostrar Listas de Tareas")
        println("3. Editar Lista de Tareas")
        println("4. Eliminar Lista de Tareas")
        println("5. Crear Tarea en Lista")
        println("6. Editar Tarea")
        println("7. Eliminar Tarea")
        println("8. Salir")
        print("Seleccione una opción: ")

        when (val opcion = scanner.nextInt()) {
            1 -> {
                print("Ingrese el nombre de la nueva lista: ")
                val nombreLista = scanner.next()
                val nuevaLista = ListaDeTareas(controladorListaDeTareas.obtenerNuevaId(), nombreLista, Date())
                controladorListaDeTareas.crearListaDeTareas(nuevaLista)
            }
            2 -> controladorListaDeTareas.mostrarListasDeTareas()
            3 -> {
                print("Ingrese el ID de la lista a editar: ")
                val idLista = scanner.nextInt()
                print("Ingrese el nuevo nombre de la lista: ")
                val nuevoNombreLista = scanner.next()
                controladorListaDeTareas.editarListaDeTareas(idLista, nuevoNombreLista)
            }
            4 -> {
                print("Ingrese el ID de la lista a eliminar: ")
                val idListaEliminar = scanner.nextInt()
                controladorListaDeTareas.eliminarListaDeTareas(idListaEliminar)
            }
            5 -> {
                // Mostrar las listas de tareas disponibles
                controladorListaDeTareas.mostrarListasDeTareas()

                print("Ingrese el número de la lista para agregar la tarea: ")
                val numeroLista = scanner.nextInt()

                val listaElegida = controladorListaDeTareas.obtenerListaPorNumero(numeroLista)

                if (listaElegida != null) {
                    print("Ingrese la descripción de la nueva tarea: ")
                    val descripcionTarea = scanner.next()
                    print("Ingrese la fecha de vencimiento de la tarea (en formato dd/MM/yyyy): ")
                    val fechaVencimientoInput = scanner.next()
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                    try {
                        val fechaVencimientoTarea = sdf.parse(fechaVencimientoInput)

                        print("La tarea está completada (true/false): ")
                        val estaCompletadaTarea = scanner.nextBoolean()

                        print("Ingrese la prioridad de la tarea: ")
                        val prioridadTarea = scanner.nextInt()

                        val nuevaTarea = Tarea(
                            listaElegida.tareas.size + 1,
                            descripcionTarea,
                            fechaVencimientoTarea,
                            estaCompletadaTarea,
                            prioridadTarea
                        )

                        controladorListaDeTareas.crearTareaEnLista(listaElegida.id, nuevaTarea)
                    } catch (e: Exception) {
                        println("Error al ingresar la fecha. Asegúrate de utilizar el formato dd/MM/yyyy. Operación cancelada.")
                    }
                } else {
                    println("Número de lista no válido. Operación cancelada.")
                }
            }

            6 -> {
                // Mostrar las listas de tareas disponibles
                controladorListaDeTareas.mostrarListasDeTareas()

                print("Ingrese el número de la lista que contiene la tarea a editar: ")
                val numeroLista = scanner.nextInt()

                val listaElegida = controladorListaDeTareas.obtenerListaPorNumero(numeroLista)

                if (listaElegida != null) {
                    // Mostrar las tareas de la lista seleccionada
                    listaElegida.tareas.forEachIndexed { index, tarea ->
                        println("$index. Tarea ID: ${tarea.id}, Descripción: ${tarea.descripcion}")
                    }

                    print("Ingrese el número de la tarea a editar: ")
                    val numeroTarea = scanner.nextInt()

                    if (numeroTarea in 0 until listaElegida.tareas.size) {
                        val tareaElegida = listaElegida.tareas[numeroTarea]

                        print("Ingrese la nueva descripción de la tarea: ")
                        val nuevaDescripcionTarea = scanner.next()
                        print("Ingrese la nueva fecha de vencimiento de la tarea (en formato dd/MM/yyyy): ")
                        val fechaVencimientoInput = scanner.next()
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                        try {
                            val nuevaFechaVencimientoTarea = sdf.parse(fechaVencimientoInput)

                        print("La tarea está completada (true/false): ")
                        val nuevaEstaCompletadaTarea = scanner.nextBoolean()
                        print("Ingrese la nueva prioridad de la tarea: ")
                        val nuevaPrioridadTarea = scanner.nextInt()

                        controladorListaDeTareas.editarTarea(
                            numeroLista,
                            tareaElegida.id,
                            nuevaDescripcionTarea,
                            nuevaFechaVencimientoTarea,
                            nuevaEstaCompletadaTarea,
                            nuevaPrioridadTarea
                        )
                        } catch (e: Exception) {
                            println("Error al ingresar la fecha. Asegúrate de utilizar el formato dd/MM/yyyy. Operación cancelada.")
                        }
                    } else {
                        println("Número de tarea no válido. Operación cancelada.")
                    }
                } else {
                    println("Número de lista no válido. Operación cancelada.")
                }
            }

            7 -> {
                // Mostrar las listas de tareas disponibles
                controladorListaDeTareas.mostrarListasDeTareas()

                print("Ingrese el número de la lista que contiene la tarea a eliminar: ")
                val numeroLista = scanner.nextInt()

                val listaElegida = controladorListaDeTareas.obtenerListaPorNumero(numeroLista)

                if (listaElegida != null) {
                    // Mostrar las tareas de la lista seleccionada
                    listaElegida.tareas.forEachIndexed { index, tarea ->
                        println("$index. Tarea ID: ${tarea.id}, Descripción: ${tarea.descripcion}")
                    }

                    print("Ingrese el número de la tarea a eliminar: ")
                    val numeroTarea = scanner.nextInt()

                    if (numeroTarea in 0 until listaElegida.tareas.size) {
                        val tareaElegida = listaElegida.tareas[numeroTarea]

                        controladorListaDeTareas.eliminarTarea(numeroLista, tareaElegida.id)
                    } else {
                        println("Número de tarea no válido. Operación cancelada.")
                    }
                } else {
                    println("Número de lista no válido. Operación cancelada.")
                }
            }

            8 -> {
                println("Saliendo del programa. ¡Hasta luego!")
                return
            }
            else -> println("Opción no válida. Por favor, elija una opción válida.")
        }

        println()
    }
}


package com.example.examne_victor_moreno

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : ComponentActivity() {

    private lateinit var taskCrud: TaskCrud
    private lateinit var taskArrayAdapter: ArrayAdapter<Task>
    private lateinit var listViewTasks: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar el objeto TaskCrud
        taskCrud = TaskCrud(this)
        taskCrud.open()

        // Obtener la lista de tareas y configurar el adaptador para el ListView
        val tasks = taskCrud.getAllTasks()

        listViewTasks = findViewById(R.id.task_list_view)

        if (tasks.isEmpty()) {
            // Si no hay tareas, agregar un mensaje en la ListView
            val emptyMessage = TextView(this)
            emptyMessage.text = "No hay tareas para mostrar"
            listViewTasks.addHeaderView(emptyMessage)
            Log.d("MainActivity", "No hay tareas para mostrar")
        } else {
            // Configurar el adaptador si hay tareas
            taskArrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks)
            listViewTasks.adapter = taskArrayAdapter

            // Agregar el menú contextual al ListView
            registerForContextMenu(listViewTasks)

            Log.d("MainActivity", "Tareas cargadas en la ListView")
        }

        // Configurar el botón para ir a la actividad CreateTaskActivity
        val botonAnadirTarea = findViewById<Button>(R.id.buttonAddTask)
        botonAnadirTarea.setOnClickListener {
            Log.d("MainActivity", "Botón 'Agregar Tarea' presionado")
            irActividad(CreateTaskActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        // Actualizar la lista cada vez que la actividad se reanuda
        val tasks = taskCrud.getAllTasks()

        if (tasks.isEmpty()) {
            val emptyMessage = TextView(this)
            emptyMessage.text = "No hay tareas para mostrar"
            listViewTasks.addHeaderView(emptyMessage)
            Log.d("MainActivity", "No hay tareas para mostrar")
        } else {
            taskArrayAdapter.clear()
            taskArrayAdapter.addAll(tasks)
            taskArrayAdapter.notifyDataSetChanged()
            Log.d("MainActivity", "Tareas actualizadas en la ListView")
        }
    }

    // Método para iniciar una actividad
    private fun irActividad(clase: Class<*>) {
        Log.d("MainActivity", "Iniciando actividad: ${clase.simpleName}")
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    // Método para crear el menú contextual
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    // Manejar las acciones del menú contextual
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedItem = taskArrayAdapter.getItem(info.position)

        when (item.itemId) {
            R.id.menu_edit -> {
                // Editar la tarea
                editTask(selectedItem)
                return true
            }
            R.id.menu_delete -> {
                // Eliminar la tarea
                deleteTask(selectedItem)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    private fun editTask(task: Task?) {
        // Abre la actividad de edición y pasa la tarea seleccionada
        task?.let {
            val intent = Intent(this, EditTaskActivity::class.java)
            intent.putExtra("taskId", it.id)
            startActivity(intent)
        }
    }

    private fun deleteTask(task: Task?) {
        // Muestra un cuadro de diálogo de confirmación antes de eliminar
        task?.let {
            val builder = AlertDialog.Builder(this, R.style.AlertDialogStyle)

            builder.setTitle("Eliminar tarea")
                .setMessage("¿Estás seguro de que quieres eliminar esta tarea?")
                .setPositiveButton("Sí") { _, _ ->
                    // Elimina la tarea
                    taskCrud.deleteTask(it.id)
                    // Actualiza la lista después de eliminar
                    updateTaskList()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun updateTaskList() {
        // Actualiza la lista cada vez que sea necesario
        val tasks = taskCrud.getAllTasks()
        taskArrayAdapter.clear()
        taskArrayAdapter.addAll(tasks)
        taskArrayAdapter.notifyDataSetChanged()
    }

}

package com.example.examne_victor_moreno

import CreateTaskActivity
import MyTask
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
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    private lateinit var taskFirestoreHelper: TaskFirestoreHelper
    private lateinit var taskArrayAdapter: ArrayAdapter<MyTask>
    private lateinit var listViewTasks: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa Firebase (esto puede no ser necesario si ya está inicializado en tu aplicación)
        FirebaseApp.initializeApp(this)

        // Inicializar el objeto TaskFirestoreHelper
        taskFirestoreHelper = TaskFirestoreHelper(this)

        // Obtener la lista de tareas y configurar el adaptador para el ListView
        listViewTasks = findViewById(R.id.task_list_view)
        taskArrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listViewTasks.adapter = taskArrayAdapter

        // Agregar el menú contextual al ListView
        registerForContextMenu(listViewTasks)

        // Configurar el botón para ir a la actividad CreateTaskActivity
        val botonAnadirTarea = findViewById<Button>(R.id.buttonAddTask)
        botonAnadirTarea.setOnClickListener {
            Log.d("MainActivity", "Botón 'Agregar Tarea' presionado")
            irActividad(CreateTaskActivity::class.java)
        }

        // Cargar las tareas desde Firestore
        cargarTareasDesdeFirestore()
    }
    override fun onResume() {
        super.onResume()
        // Actualizar la lista cada vez que la actividad se reanuda
        cargarTareasDesdeFirestore()
    }

    private fun cargarTareasDesdeFirestore() {
        taskFirestoreHelper.getAllTasks().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val tasks = task.result?.toObjects(MyTask::class.java)
                actualizarListView(tasks)
            } else {
                Log.e("MainActivity", "Error al obtener las tareas de Firestore", task.exception)
            }
        }
    }

    private fun actualizarListView(tasks: List<MyTask>?) {
        taskArrayAdapter.clear()
        tasks?.let {
            taskArrayAdapter.addAll(it)
        }
        taskArrayAdapter.notifyDataSetChanged()
    }

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

    private fun editTask(task: MyTask?) {
        // Abre la actividad de edición y pasa la tarea seleccionada
        task?.let {
            val intent = Intent(this, EditTaskActivity::class.java)
            intent.putExtra("taskId", it.id)
            startActivity(intent)
        }
    }

    private fun deleteTask(task: MyTask?) {
        // Muestra un cuadro de diálogo de confirmación antes de eliminar
        task?.let {
            val builder = AlertDialog.Builder(this, R.style.AlertDialogStyle)

            builder.setTitle("Eliminar tarea")
                .setMessage("¿Estás seguro de que quieres eliminar esta tarea?")
                .setPositiveButton("Sí") { _, _ ->
                    // Elimina la tarea
                    taskFirestoreHelper.deleteTask(it.id ?: "")
                    // Actualiza la lista después de eliminar
                    cargarTareasDesdeFirestore()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }


}

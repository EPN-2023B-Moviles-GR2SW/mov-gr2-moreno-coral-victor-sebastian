package com.example.MyApplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import com.example.myapplication.R

class MainActivity : ComponentActivity() {

    private lateinit var taskCrud: TaskCrud
    private lateinit var taskArrayAdapter: ArrayAdapter<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar el objeto TaskCrud
        taskCrud = TaskCrud(this)
        taskCrud.open()

        // Obtener la lista de tareas y configurar el adaptador para el ListView
        val tasks = taskCrud.getAllTasks()
        taskArrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tasks)

        val listViewTasks: ListView = findViewById(R.id.task_list_view)
        listViewTasks.adapter = taskArrayAdapter

        // Configurar el botón para ir a la actividad CreateTaskActivity
        val botonAnadirTarea = findViewById<Button>(R.id.buttonAddTask)
        botonAnadirTarea.setOnClickListener {
            irActividad(CreateTaskActivity::class.java)
        }
    }

    // Método para iniciar una actividad
    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    // Cerrar la base de datos cuando la actividad se destruye
    override fun onDestroy() {
        super.onDestroy()
        taskCrud.close()
    }
}

package com.example.examne_victor_moreno

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class EditTaskActivity : AppCompatActivity() {

    private lateinit var taskCrud: TaskCrud
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextDueDate: EditText
    private lateinit var spinnerPriority: Spinner

    // Variable para almacenar la tarea seleccionada
    private lateinit var selectedTask: Task

    // Declarar adapter como propiedad de la clase
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        taskCrud = TaskCrud(this)
        taskCrud.open()

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextDueDate = findViewById(R.id.editTextDueDate)
        spinnerPriority = findViewById(R.id.spinnerPriority)

        // Inicializar adapter
        val priorityValues = arrayOf("Alta", "Media", "Baja")
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorityValues)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = adapter

        // Obtener la tarea seleccionada de la base de datos o del intent y establecer los valores en los elementos de la interfaz de usuario.
        selectedTask = getSelectedTask() ?: Task() // Utiliza un constructor sin argumentos si no se encuentra la tarea.

        // Establecer los detalles de la tarea en la interfaz de usuario
        setTaskDetails(selectedTask)
    }

    // Método para actualizar la tarea en la base de datos
    fun updateTask(view: View) {
        // Obtener los nuevos valores de la interfaz de usuario
        val newTitle = editTextTitle.text.toString()
        val newDescription = editTextDescription.text.toString()
        val newDueDate = editTextDueDate.text.toString()
        val newPriority = spinnerPriority.selectedItem.toString()

        // Actualizar la tarea seleccionada con los nuevos valores
        selectedTask.title = newTitle
        selectedTask.description = newDescription
        selectedTask.dueDate = newDueDate
        selectedTask.priority = newPriority

        // Actualizar la tarea en la base de datos
        taskCrud.updateTask(selectedTask)

        // Cerrar la actividad y regresar a la actividad principal
        finish()
    }

    // Método para establecer los detalles de la tarea en la interfaz de usuario
    private fun setTaskDetails(task: Task) {
        editTextTitle.setText(task.title)
        editTextDescription.setText(task.description)
        editTextDueDate.setText(task.dueDate)

        // Seleccionar la prioridad correcta en el Spinner
        val priorityIndex = adapter.getPosition(task.priority)
        if (priorityIndex != -1) {
            spinnerPriority.setSelection(priorityIndex)
        }
    }

    // Método para obtener la tarea seleccionada
    private fun getSelectedTask(): Task? {
        val taskId = intent.getLongExtra("taskId", -1)

        if (taskId != -1L) {
            // Utiliza TaskCrud para obtener la tarea por su ID
            return taskCrud.getTaskById(taskId)
        }

        return null
    }

    // Cerrar la base de datos cuando la actividad se destruye
    override fun onDestroy() {
        super.onDestroy()
        taskCrud.close()
    }
}

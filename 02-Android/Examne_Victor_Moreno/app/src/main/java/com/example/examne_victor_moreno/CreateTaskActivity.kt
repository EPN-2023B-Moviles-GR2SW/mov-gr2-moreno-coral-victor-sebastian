package com.example.examne_victor_moreno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var taskCrud: TaskCrud
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextDueDate: EditText
    private lateinit var spinnerPriority: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        taskCrud = TaskCrud(this)
        taskCrud.open()

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextDueDate = findViewById(R.id.editTextDueDate)
        spinnerPriority = findViewById(R.id.spinnerPriority)

        // Configurar el adaptador para el Spinner (puedes cambiar los elementos según tus necesidades)
        val priorityValues = arrayOf("Alta", "Media", "Baja")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorityValues)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = adapter

        // Añadir OnClickListener al botón "Guardar"
        val buttonSave = findViewById<View>(R.id.buttonSave)
        buttonSave.setOnClickListener {
            saveTask(it)
        }
    }

    // Método para guardar la tarea en la base de datos
    fun saveTask(view: View) {
        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()
        val dueDate = editTextDueDate.text.toString()

        // Obtener la prioridad seleccionada del Spinner
        val priority = spinnerPriority.selectedItem.toString()

        val task = Task(
            title = title,
            description = description,
            dueDate = dueDate,
            priority = priority
        )

        val taskId = taskCrud.insertTask(task)

        // Asignar el ID a la tarea después de insertarla en la base de datos
        task.id = taskId

        // Mostrar Snackbar
        Snackbar.make(view, "Tarea guardada correctamente", Snackbar.LENGTH_SHORT).show()
    }

    // Cerrar la base de datos cuando la actividad se destruye
    override fun onDestroy() {
        super.onDestroy()
        taskCrud.close()
    }
}


package com.example.examne_victor_moreno


import MyTask
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class EditTaskActivity : AppCompatActivity() {

    private lateinit var taskFirestoreHelper: TaskFirestoreHelper
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextDueDate: EditText
    private lateinit var spinnerPriority: Spinner

    // Variable para almacenar la tarea seleccionada
    private lateinit var selectedTask: MyTask

    // Declarar adapter como propiedad de la clase
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        taskFirestoreHelper = TaskFirestoreHelper(this)

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextDueDate = findViewById(R.id.editTextDueDate)
        spinnerPriority = findViewById(R.id.spinnerPriority)

        // Initialize adapter
        val priorityValues = arrayOf("Alta", "Media", "Baja")
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorityValues)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = adapter

        // Use a coroutine to get the selected task
        lifecycleScope.launch {
            selectedTask = getSelectedTask() ?: MyTask()
            // Set the details after obtaining the task
            setTaskDetails(selectedTask)
        }
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
        selectedTask.dueDate = convertStringToTimestamp(newDueDate)
        selectedTask.priority = newPriority

        // Convertir el Map<String, Any?> a Map<String, Any>
        val updatedTaskMap: Map<String, Any> = selectedTask.toMap().filterValues { it != null } as Map<String, Any>

        // Actualizar la tarea en Firebase Firestore
        taskFirestoreHelper.updateTask(selectedTask.id ?: "", updatedTaskMap)

        // Cerrar la actividad y regresar a la actividad principal
        finish()
    }


    // Método para establecer los detalles de la tarea en la interfaz de usuario
    private fun setTaskDetails(task: MyTask) {
        editTextTitle.setText(task.title)
        editTextDescription.setText(task.description)
        editTextDueDate.setText(convertTimestampToString(task.dueDate))

        // Seleccionar la prioridad correcta en el Spinner
        val priorityIndex = adapter.getPosition(task.priority)
        if (priorityIndex != -1) {
            spinnerPriority.setSelection(priorityIndex)
        }
    }

    // Método para obtener la tarea seleccionada
    private suspend fun getSelectedTask(): MyTask? {
        val taskId = intent.getStringExtra("taskId")

        if (!taskId.isNullOrBlank()) {
            // Utilize suspendCoroutine to convert Task<MyTask?> to MyTask? using coroutines
            return suspendCoroutine { continuation ->
                taskFirestoreHelper.getTaskById(taskId)
                    .addOnSuccessListener { myTask ->
                        continuation.resume(myTask)
                    }
                    .addOnFailureListener {
                        // Handle failure if needed
                        continuation.resume(null)
                    }
            }
        }

        return null
    }


    // Convertir una cadena de fecha a un Timestamp
    private fun convertStringToTimestamp(dateString: String): Timestamp {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val parsedDate = dateFormat.parse(dateString)
        return Timestamp(parsedDate)
    }

    // Convertir un Timestamp a una cadena de fecha
    private fun convertTimestampToString(timestamp: Timestamp?): String {
        if (timestamp != null) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return dateFormat.format(timestamp.toDate())
        }
        return ""
    }

    // Cerrar la base de datos cuando la actividad se destruye
    override fun onDestroy() {
        super.onDestroy()
        // Puedes cerrar la conexión de Firebase Firestore aquí si es necesario
    }
}

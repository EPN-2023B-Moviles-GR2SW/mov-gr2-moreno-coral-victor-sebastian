import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.examne_victor_moreno.R
import com.example.examne_victor_moreno.TaskFirestoreHelper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var taskFirestoreHelper: TaskFirestoreHelper
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextDueDate: EditText
    private lateinit var spinnerPriority: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        taskFirestoreHelper = TaskFirestoreHelper(this)

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

    // Método para guardar la tarea en la base de datos de Firebase
    fun saveTask(view: View) {
        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()
        val dueDate = editTextDueDate.text.toString()

        // Convertir la cadena 'dueDate' a un Timestamp
        val timestamp = convertStringToTimestamp(dueDate)

        // Obtener la prioridad seleccionada del Spinner
        val priority = spinnerPriority.selectedItem.toString()

        val myTask = MyTask(
            title = title,
            description = description,
            dueDate = timestamp,
            priority = priority
        )

        // Agregar la tarea a Firebase Firestore
        taskFirestoreHelper.addTask(myTask)

        // Mostrar Snackbar
        Snackbar.make(view, "Tarea guardada correctamente", Snackbar.LENGTH_SHORT).show()
    }

    // Convertir una cadena de fecha a un Timestamp
    private fun convertStringToTimestamp(dateString: String): Timestamp {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val parsedDate = dateFormat.parse(dateString)
        return Timestamp(parsedDate)
    }

    // No olvides cerrar recursos o conexiones al destruir la actividad
    override fun onDestroy() {
        super.onDestroy()
        // Puedes cerrar la conexión de Firebase Firestore aquí si es necesario
    }
}

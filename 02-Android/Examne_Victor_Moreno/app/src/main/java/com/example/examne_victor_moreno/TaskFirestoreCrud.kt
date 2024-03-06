import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class TaskFirestoreCrud {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val taskCollection: CollectionReference = firestore.collection(Task.COLLECTION_NAME)

    fun insertTask(task: Task): Task<Void> {
        return taskCollection.add(task.toMap())
    }

    fun getAllTasks(): Task<List<Task>> {
        return taskCollection.get().continueWith { task ->
            val result = mutableListOf<Task>()
            for (document in task.result!!) {
                val id = document.id
                val taskData = document.data
                val taskObject = Task.fromMap(id, taskData)
                result.add(taskObject)
            }
            result
        }
    }

    fun updateTask(task: Task): Task<Void> {
        val taskDocument = taskCollection.document(task.id ?: "")
        return taskDocument.update(task.toMap())
    }

    fun getTaskById(taskId: String): Task<DocumentSnapshot> {
        val taskDocument = taskCollection.document(taskId)
        return taskDocument.get()
    }

    fun deleteTask(taskId: String): Task<Void> {
        val taskDocument = taskCollection.document(taskId)
        return taskDocument.delete()
    }
}

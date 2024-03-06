package com.example.examne_victor_moreno

import MyTask
import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskFirestoreHelper(context: Context) {

    companion object {
        const val COLLECTION_NAME = "tasks"
    }

    private val firestore = FirebaseFirestore.getInstance()

    // Agregar una tarea a Firestore
    fun addTask(myTask: MyTask) {
        val taskMap = hashMapOf(
            "title" to myTask.title,
            "description" to myTask.description,
            "due_date" to myTask.dueDate,
            "priority" to myTask.priority
        )

        firestore.collection(COLLECTION_NAME)
            .add(taskMap)
    }


    // Obtener todas las tareas de Firestore
    fun getAllTasks(): Task<QuerySnapshot> {
        return firestore.collection(COLLECTION_NAME)
            .get()
    }

    // Eliminar una tarea de Firestore
    fun deleteTask(taskId: String) {
        firestore.collection(COLLECTION_NAME)
            .document(taskId)
            .delete()
    }

    // Actualizar una tarea en Firestore
    fun updateTask(taskId: String, updatedTask: Map<String, Any>) {
        firestore.collection(COLLECTION_NAME)
            .document(taskId)
            .update(updatedTask)
    }
    // Change the return type of getTaskById to Task<MyTask?>
    fun getTaskById(taskId: String): Task<MyTask?> {
        return firestore.collection(COLLECTION_NAME).document(taskId).get()
            .continueWith { task ->
                if (task.isSuccessful) {
                    val snapshot = task.result
                    if (snapshot.exists()) {
                        val myTask = snapshot.toObject(MyTask::class.java)
                        myTask
                    } else {
                        null
                    }
                } else {
                    null
                }
            }
    }



}

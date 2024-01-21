package com.example.examne_victor_moreno

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase

class TaskCrud(context: Context) {
    private lateinit var database: SQLiteDatabase
    private var dbHelper: TaskDBHelper = TaskDBHelper(context)

    init {
        dbHelper = TaskDBHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun insertTask(task: Task): Long {
        val values = ContentValues()
        values.put(TaskDBHelper.COLUMN_TITLE, task.title)
        values.put(TaskDBHelper.COLUMN_DESCRIPTION, task.description)
        values.put(TaskDBHelper.COLUMN_DUE_DATE, task.dueDate)
        values.put(TaskDBHelper.COLUMN_PRIORITY, task.priority)

        return database.insert(TaskDBHelper.TABLE_NAME, null, values)
    }

    fun getAllTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val cursor = database.query(
            TaskDBHelper.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            it.moveToFirst()
            while (!it.isAfterLast) {
                val task = cursorToTask(it)
                tasks.add(task)
                it.moveToNext()
            }
        }

        return tasks
    }

    fun updateTask(task: Task): Int {
        val values = ContentValues()
        values.put(TaskDBHelper.COLUMN_TITLE, task.title)
        values.put(TaskDBHelper.COLUMN_DESCRIPTION, task.description)
        values.put(TaskDBHelper.COLUMN_DUE_DATE, task.dueDate)
        values.put(TaskDBHelper.COLUMN_PRIORITY, task.priority)

        return database.update(
            TaskDBHelper.TABLE_NAME,
            values,
            "${TaskDBHelper.COLUMN_ID} = ?",
            arrayOf(task.id.toString())
        )
    }
    fun getTaskById(taskId: Long): Task {
        val cursor = database.query(
            TaskDBHelper.TABLE_NAME,
            null,
            "${TaskDBHelper.COLUMN_ID} = ?",
            arrayOf(taskId.toString()),
            null,
            null,
            null
        )

        return cursor?.use {
            if (it.moveToFirst()) {
                cursorToTask(it)
            } else {
                // Si no se encuentra la tarea, puedes lanzar una excepción o devolver un valor predeterminado.
                // Por ejemplo, lanzar una excepción:
                throw NoSuchElementException("No se encontró la tarea con el ID: $taskId")

                // O devolver un valor predeterminado (asegúrate de que Task tenga un constructor sin argumentos)
                // Task()
            }
        } ?: throw IllegalStateException("El cursor es nulo al intentar obtener la tarea con el ID: $taskId")
    }


    fun deleteTask(taskId: Long?): Int {
        return database.delete(
            TaskDBHelper.TABLE_NAME,
            "${TaskDBHelper.COLUMN_ID} = ?",
            arrayOf(taskId.toString())
        )
    }

    fun cursorToTask(cursor: Cursor): Task {
        return Task(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(TaskDBHelper.COLUMN_ID)),
            title = cursor.getString(cursor.getColumnIndexOrThrow(TaskDBHelper.COLUMN_TITLE)),
            description = cursor.getString(cursor.getColumnIndexOrThrow(TaskDBHelper.COLUMN_DESCRIPTION)),
            dueDate = cursor.getString(cursor.getColumnIndexOrThrow(TaskDBHelper.COLUMN_DUE_DATE)),
            priority = cursor.getString(cursor.getColumnIndexOrThrow(TaskDBHelper.COLUMN_PRIORITY))
        )
    }
}

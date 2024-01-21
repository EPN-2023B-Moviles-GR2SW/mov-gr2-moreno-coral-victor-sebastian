package com.example.MyApplication

class Task(
    var id: Int? = null,
    var title: String,
    var description: String,
    var dueDate: String,
    var priority: String
) {

    override fun toString(): String {
        return "ID: $id, Title: $title, Priority: $priority"
    }
}

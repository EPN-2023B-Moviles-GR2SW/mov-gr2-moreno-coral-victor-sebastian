import com.google.firebase.Timestamp

data class MyTask(
    var id: String? = null,
    var title: String = "",
    var description: String = "",
    var dueDate: Timestamp? = null,
    var priority: String = ""
) {

    companion object {
        const val COLLECTION_NAME = "tasks"

        fun fromMap(id: String, data: Map<String, Any?>): MyTask {
            return MyTask(
                id,
                data["title"] as? String ?: "",
                data["description"] as? String ?: "",
                data["dueDate"] as? Timestamp,
                data["priority"] as? String ?: ""
            )
        }
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "title" to title,
            "description" to description,
            "dueDate" to dueDate,
            "priority" to priority
        )
    }
}

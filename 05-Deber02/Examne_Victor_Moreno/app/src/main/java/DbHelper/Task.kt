package DbHelper

class Task(
    var id: Long? = null,
    var title: String = "",
    var description: String = "",
    var dueDate: String = "",
    var priority: String = ""
) {

    override fun toString(): String {
        return "ID: $id, Title: $title, Priority: $priority"
    }
}

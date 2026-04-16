data class TodoResponse(
    val id: Long,
    val title: String,
    val content: String,
    val completed: Boolean = false,
    val createdAt: ZonedDateTime? = null,
    val updatedAt: ZonedDateTime? = null
) {
    companion object {
        fun from(entity: Todo): TodoResponse =
            TodoResponse(
                id = entity.id,
                title = entity.title,
                content = entity.content,
                completed = entity.completed,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt
            )
    }
}
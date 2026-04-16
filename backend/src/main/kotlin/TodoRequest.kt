data class TodoRequest(
    @field:NotBlank(message = "Title cannot be blank")
    val title: String,

    @field:NotBlank(message = "Content cannot be blank")
    val content: String,

    val completed: Boolean = false
)
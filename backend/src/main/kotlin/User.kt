/**
 * Represents the core domain model.
 */
data class User(
    val id: Long? = null,
    val name: String,
    val email: String
)

/**
 * DTO for creating a user.
 */
data class UserCreateRequest(
    val name: String,
    val email: String
)

/**
 * DTO for returning user information.
 */
data class UserResponse(
    val id: Long,
    val name: String,
    val email: String
) {
    companion object {
        fun from(user: User): UserResponse =
            UserResponse(id = user.id ?: 0L, name = user.name, email = user.email)
    }
}
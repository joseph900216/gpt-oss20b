import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

/**
 * Service to handle business logic for User operations.
 */
@Service
class UserService {
    // Simulating a database with a thread-safe ID generator
    private val idGenerator = AtomicLong(1L)
    private val users = mutableListOf<User>()

    fun createUser(request: UserCreateRequest): User {
        val newUser = User(
            id = idGenerator.getAndIncrement(),
            name = request.name,
            email = request.email
        )
        users.add(newUser)
        return newUser
    }

    fun getUserById(id: Long): User {
        return users.find { it.id == id } 
            ?: throw NoSuchElementException("User with ID $id not found.")
    }

    fun getAllUsers(): List<User> = users.toList()

    fun deleteUser(id: Long) {
        val user = getUserById(id)
        users.remove(user)
    }
}
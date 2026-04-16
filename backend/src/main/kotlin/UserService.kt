@Service
@Transactional(readOnly = true)
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun createUser(request: UserRequest): User = userRepository.save(User.from(request))

    @Transactional
    fun updateUser(id: Long, request: UserRequest): User {
        val user = userRepository.findById(id).orElseThrow { ResourceNotFoundException(...) }
        user.apply { name = request.name; email = request.email; age = request.age }
        return userRepository.save(user)
    }

    @Transactional
    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }
}
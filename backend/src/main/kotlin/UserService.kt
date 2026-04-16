// 2. 비밀번호 해시 처리 (Spring Security BCryptPasswordEncoder 사용)
@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder   // BCryptPasswordEncoder 빈
) {

    fun registerUser(username: String, email: String, rawPassword: String): UserDto {
        require(userRepository.findByUsername(username) == null) { "Username already exists" }
        require(userRepository.findByEmail(email) == null) { "Email already exists" }

        val hashedPassword = passwordEncoder.encode(rawPassword)
        val user = User(username = username, email = email, password = hashedPassword)
        val saved = userRepository.save(user)
        return UserDto.fromEntity(saved)
    }
}
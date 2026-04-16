// ----------------------------------------------------------------------
// User.kt (Entity)
// ----------------------------------------------------------------------
@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_user_username", columnNames = ["username"]),
        UniqueConstraint(name = "uk_user_email", columnNames = ["email"])
    ]
)
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(nullable = false, length = 50) val username: String,
    @Column(nullable = false, length = 100) val email: String,
    @Column(nullable = false) val createdAt: LocalDateTime = LocalDateTime.now()
)

// ----------------------------------------------------------------------
// UserRepository.kt
// ----------------------------------------------------------------------
interface UserRepository : JpaRepository<User, Long> {
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
}

// ----------------------------------------------------------------------
// UserUpdateDTO.kt
// ----------------------------------------------------------------------
data class UserUpdateDTO(
    @field:Size(max = 20, message = "최대 20자 이내로 입력해 주세요.")
    val username: String? = null,

    @field:Email(message = "이메일 형식이 올바르지 않습니다.")
    val email: String? = null
)

// ----------------------------------------------------------------------
// UserService.kt
// ----------------------------------------------------------------------
@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun createUser(request: UserCreateDTO): UserResponseDTO {
        // DB 레벨에서 UNIQUE 제약이 있으므로, 예외 발생 시 정상적으로 중복 처리
        val user = User(
            username = request.username,
            email = request.email
        )
        return userRepository.save(user).toResponseDTO()
    }

    override fun updateUser(id: Long, request: UserUpdateDTO): UserResponseDTO {
        val user = userRepository.findById(id).orElseThrow {
            ResourceNotFoundException("id=$id 에 해당하는 사용자를 찾을 수 없습니다.")
        }

        // 새 객체를 만들지 않고 기존 엔티티를 수정
        if (request.username != null) user.username = request.username
        if (request.email != null) user.email = request.email
        return userRepository.save(user).toResponseDTO()
    }

    // 기타 메서드는 기존 로직 그대로 유지
}

// Extension 함수 (Entity → DTO 변환)
private fun User.toResponseDTO() = UserResponseDTO(
    id = this.id!!,
    username = this.username,
    email = this.email
)
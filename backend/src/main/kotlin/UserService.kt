import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun createUser(request: UserRequestDto): UserResponseDto {
        if (userRepository.existsByEmail(request.email)) {
            throw AlreadyExistsException("이미 존재하는 이메일입니다.")
        }
        val user = userRepository.save(User(name = request.name, email = request.email))
        return user.toResponseDto()
    }

    fun getUser(id: Long): UserResponseDto {
        return userRepository.findById(id).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }.toResponseDto()
    }

    @Transactional
    fun updateUser(id: Long, request: UserRequestDto): UserResponseDto {
        val user = userRepository.findById(id).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }
        user.update(request.name, request.email)
        return user.toResponseDto()
    }

    private fun User.toResponseDto() = UserResponseDto(name = this.name, email = this.email)
}

// filename: UserController.kt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun create(@RequestBody request: UserRequestDto) = userService.createUser(request)

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = userService.getUser(id)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: UserRequestDto) = userService.updateUser(id, request)
}
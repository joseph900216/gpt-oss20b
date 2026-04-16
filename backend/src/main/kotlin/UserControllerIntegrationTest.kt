@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIntegrationTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val userRepository: UserRepository
) {

    @Test
    fun `회원가입 성공`() {
        val request = mapOf(
            "username" to "newuser",
            "email" to "new@example.com",
            "password" to "Password123!"
        )
        mockMvc.post("/api/users") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isCreated() }
            jsonPath("$.username") { value("newuser") }
            jsonPath("$.email") { value("new@example.com") }
            jsonPath("$.createdAt") { exists() }
        }
    }

    // 중복 이메일, 중복 사용자명, 비밀번호 검증 등 추가 테스트
}
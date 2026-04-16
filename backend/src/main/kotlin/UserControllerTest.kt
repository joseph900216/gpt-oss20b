// ----------------------------------------------------------------------
// UserControllerTest.kt (경량 테스트 예시)
// ----------------------------------------------------------------------
@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired lateinit var mockMvc: MockMvc

    @MockBean lateinit var userService: UserService

    @Test
    fun `PUT /users/{id} - username & email change`() {
        val dto = UserUpdateDTO(username = "newUser", email = "new@example.com")
        `when`(userService.updateUser(1L, dto)).thenReturn(
            UserResponseDTO(1L, "newUser", "new@example.com")
        )

        mockMvc.perform(
            MockMvcRequestBuilders.put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.username").value("newUser"))
            .andExpect(jsonPath("$.email").value("new@example.com"))
    }
}
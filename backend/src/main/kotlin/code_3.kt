// UserControllerTest.kt – adjust the mock expectation
   @Test
   fun `POST /users returns created UserDto`() {
       val request = UserRequest(name = "Tom", email = "tom@example.com", age = 22)

       val responseDto = com.example.userapi.dto.UserDto(
           id = 1L,
           name = "Tom",
           email = "tom@example.com",
           age = 22
       )

       mockiWhen(userService.createUser(request)).thenReturn(responseDto)

       mockMvc.perform(
           post("/users")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(request))
       ).andExpect(status().isCreated)
           .andExpect(jsonPath("$.id").value(1L))
           .andExpect(jsonPath("$.name").value("Tom"))
           .andExpect(jsonPath("$.email").value("tom@example.com"))
           .andExpect(jsonPath("$.age").value(22))
   }
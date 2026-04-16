// UserController.kt
@GetMapping
fun listUsers(@PageableDefault(size = 20) pageable: Pageable) =
    userService.listUsers(pageable).map { UserResponse.from(it) }
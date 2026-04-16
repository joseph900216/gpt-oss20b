// UserService.kt
fun listUsers(pageable: Pageable): Page<User> = userRepository.findAll(pageable)
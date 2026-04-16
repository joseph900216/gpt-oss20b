fun updateUser(id: Long, request: UserRequest): UserDto {
       val existingEntity = userRepository.findById(id)
               .orElseThrow { ResourceNotFoundException("User with id $id not found") }

       // If e‑mail is being changed, ensure it is still unique
       if (request.email != existingEntity.email) {
           userRepository.findByEmail(request.email).let { existingWithEmail ->
               if (existingWithEmail != null && existingWithEmail.id != id) {
                   throw IllegalArgumentException("User with e‑mail ${request.email} already exists")
               }
           }
       }

       val updated = existingEntity.copy(
           name = request.name,
           email = request.email,
           age = request.age
       )
       val saved = userRepository.save(updated)
       return UserDto(saved.id!!, saved.name, saved.email, saved.age)
   }
// Add @Transactional annotations to all mutating methods
   @Transactional
   fun createUser(request: UserRequest): UserDto { … }

   @Transactional
   fun updateUser(id: Long, request: UserRequest): UserDto { … }

   @Transactional
   fun deleteUser(id: Long) { … }
@ExceptionHandler(IllegalArgumentException::class)
   fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<Map<String, String>> {
       val body = mapOf("error" to (ex.message ?: "Bad request"))
       return ResponseEntity(body, HttpStatus.BAD_REQUEST)
   }
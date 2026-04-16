val body = mapOf("error" to (ex.message ?: "Invalid request"))
   return ResponseEntity(body, HttpStatus.BAD_REQUEST)
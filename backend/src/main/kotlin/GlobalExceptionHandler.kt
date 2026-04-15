@ControllerAdvice
   class GlobalExceptionHandler {
       @ExceptionHandler(Exception::class)
       fun handleException(ex: Exception): ResponseEntity<ErrorResponseDto> {
           val dto = ErrorResponseDto("internal server error")
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto)
       }
   }
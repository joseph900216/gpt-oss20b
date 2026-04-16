@ExceptionHandler(Exception::class)
fun handleException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
    val body = mapOf("message" to "서버 내부 오류가 발생했습니다.")
    return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
}
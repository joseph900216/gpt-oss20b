// ----------------------------------------------------------------------
// RestExceptionHandler.kt (예외 처리 개선)
// ----------------------------------------------------------------------
@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDuplicate(ex: DataIntegrityViolationException): ResponseEntity<Any> {
        val msg = ex.localizedMessage?.substringAfter("Duplicate key value").trim() ?: "중복된 데이터가 존재합니다."
        return ResponseEntity(
            mapOf(
                "error" to mapOf("code" to "409", "message" to msg)
            ),
            HttpStatus.CONFLICT
        )
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException): ResponseEntity<Any> {
        return ResponseEntity(
            mapOf(
                "error" to mapOf("code" to "404", "message" to ex.message ?: "Not Found")
            ),
            HttpStatus.NOT_FOUND
        )
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors.map {
            mapOf("field" to it.field, "message" to it.defaultMessage!!)
        }
        return ResponseEntity(
            mapOf(
                "error" to mapOf("code" to status.value(), "message" to "필드 검증 오류"),
                "details" to errors
            ),
            status
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception): ResponseEntity<Any> {
        // 예외 메시지는 내부에서만 노출
        return ResponseEntity(
            mapOf(
                "error" to mapOf("code" to "500", "message" to "알 수 없는 서버 오류가 발생했습니다.")
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
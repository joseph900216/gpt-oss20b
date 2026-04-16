// 3. 비밀번호 검증 시 BCrypt 사용
fun checkPassword(rawPassword: String, encodedPassword: String): Boolean {
    return passwordEncoder.matches(rawPassword, encodedPassword)
}
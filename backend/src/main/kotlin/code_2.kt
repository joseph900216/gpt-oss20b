// UserRepository.kt – 새 메서드 추가
interface UserRepository : JpaRepository<User, Long> {
    fun findAll(pageable: Pageable): Page<User>
}
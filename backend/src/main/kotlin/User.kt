// 1. User 엔티티에 createdAt 자동 설정
@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var password: String,   // 비밀번호는 해시값으로 저장

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null
) {
    @PrePersist
    fun prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now()
    }
}
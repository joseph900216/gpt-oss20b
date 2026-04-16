@Entity
data class Todo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @field:NotBlank(message = "Title must not be blank")
    var title: String,

    @field:NotBlank(message = "Content must not be blank")
    var content: String,

    var completed: Boolean = false,

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    var board: Board? = null,

    /** --------‑ 감사 및 타임스탬프 --------‑ */
    @CreatedDate
    @Column(updatable = false)
    var createdAt: ZonedDateTime? = null,

    @LastModifiedDate
    var updatedAt: ZonedDateTime? = null,

    @Version
    var version: Int? = null       // Optimistic Lock
)
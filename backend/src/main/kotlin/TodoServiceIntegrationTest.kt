@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TodoServiceIntegrationTest(
    @Autowired val todoRepository: TodoRepository,
    @Autowired val boardRepository: BoardRepository,
    @Autowired val todoService: TodoService
) {

    lateinit var testBoard: Board

    @BeforeAll
    fun setUp() {
        testBoard = boardRepository.save(Board(title = "Test Board"))
    }

    @AfterAll
    fun tearDown() {
        todoRepository.deleteAll()
        boardRepository.deleteAll()
    }

    @Test
    fun `create todo successfully`() {
        val request = TodoRequest(
            boardId = testBoard.id,
            title = "새 글",
            content = "새 내용"
        )
        val response = todoService.createTodo(request)

        assertThat(response.id).isGreaterThan(0)
        assertThat(response.title).isEqualTo("새 글")
        assertThat(response.content).isEqualTo("새 내용")
    }

    // …(다른 테스트도 Service 레이어를 직접 테스트)
}
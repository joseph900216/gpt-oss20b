@Service
@RequiredArgsConstructor @field:Lazy // 필요 시 Lazy 로딩
class TodoService(
    private val todoRepository: TodoRepository,
    private val boardRepository: BoardRepository
) {

    @Transactional
    fun createTodo(request: TodoRequest): TodoResponse =
        Board::class.java.run {
            val board = boardRepository.findById(request.boardId)
                .orElseThrow { ResourceNotFoundException("Board not found.") }

            val todo = Todo(
                title = request.title,
                content = request.content,
                completed = request.completed,
                board = board
            )
            todoRepository.save(todo)
            TodoResponse.from(todo)
        }

    fun findTodoById(id: Long): TodoResponse =
        todoRepository.findById(id)
            ?.let(TodoResponse::from)
            ?: throw ResourceNotFoundException("Todo not found.")

    @Transactional
    fun updateTodo(id: Long, request: TodoRequest): TodoResponse =
        todoRepository.findById(id)
            ?.let {
                it.title = request.title
                it.content = request.content
                it.completed = request.completed
                todoRepository.save(it)
                TodoResponse.from(it)
            }
            ?: throw ResourceNotFoundException("Todo not found.")

    @Transactional
    fun deleteTodo(id: Long) {
        if (!todoRepository.existsById(id))
            throw ResourceNotFoundException("Todo not found.")
        todoRepository.deleteById(id)
    }

    fun getAllTodos(page: Int, size: Int): Page<TodoResponse> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by("id").descending())
        return todoRepository.findAll(pageable).map { TodoResponse.from(it) }
    }
}
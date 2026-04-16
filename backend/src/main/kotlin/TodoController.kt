@RestController
@RequestMapping("/todos")
@Api(tags = ["Todo API"])
@RequiredArgsConstructor
class TodoController(
    private val todoService: TodoService
) {

    @PostMapping
    @ApiOperation("Create a new Todo")
    fun create(@Valid @RequestBody request: TodoRequest): ResponseEntity<TodoResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(request))

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ResponseEntity<TodoResponse> =
        ResponseEntity.ok(todoService.findTodoById(id))

    @GetMapping
    fun findAll(
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "size", defaultValue = "20") size: Int
    ): ResponseEntity<Page<TodoResponse>> {
        val data = todoService.getAllTodos(page, size)
        return ResponseEntity.ok(data)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: TodoRequest
    ): ResponseEntity<TodoResponse> =
        ResponseEntity.ok(todoService.updateTodo(id, request))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        todoService.deleteTodo(id)
        return ResponseEntity.noContent().build()
    }
}
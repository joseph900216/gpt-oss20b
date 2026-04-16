# 1️⃣ Create
curl -X POST http://localhost:8080/api/v1/users \
  -H 'Content-Type: application/json' \
  -d '{"name":"Alice","email":"alice@example.com"}'

# 2️⃣ List All
curl http://localhost:8080/api/v1/users

# 3️⃣ Get one
curl http://localhost:8080/api/v1/users/<id>

# 4️⃣ Update
curl -X PUT http://localhost:8080/api/v1/users/<id> \
  -H 'Content-Type: application/json' \
  -d '{"name":"Alice Updated","email":"alice@new.com"}'

# 5️⃣ Delete
curl -X DELETE http://localhost:8080/api/v1/users/<id>
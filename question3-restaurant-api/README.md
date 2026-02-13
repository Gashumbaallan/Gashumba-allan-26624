# Question3 - Restaurant Menu API

This is a minimal Spring Boot REST API for a restaurant menu.

Quick run (PowerShell):

1) Reduce Maven JVM memory and build:

```powershell
$env:MAVEN_OPTS='-Xms64m -Xmx256m'
mvn -DskipTests=true clean package
```

2) Run packaged jar with small heap:

```powershell
java -Xms64m -Xmx256m -jar target\question3-restaurant-api-0.0.1-SNAPSHOT.jar
```

3) Or run via Spring Boot plugin with small heap:

```powershell
mvn -DskipTests=true spring-boot:run -Dspring-boot.run.jvmArguments="-Xms64m -Xmx256m"
```

Base URL: `http://localhost:8080/api/menu`

Sample requests:

- GET all: `curl http://localhost:8080/api/menu`
- GET by id: `curl http://localhost:8080/api/menu/1`
- GET by category: `curl http://localhost:8080/api/menu/category/Main%20Course`
- GET available: `curl "http://localhost:8080/api/menu/available?available=true"`
- Search: `curl "http://localhost:8080/api/menu/search?name=pizza"`
- Create (JSON):

```bash
curl -X POST http://localhost:8080/api/menu -H "Content-Type: application/json" -d '{"name":"Garlic Bread","description":"Toasted garlic bread","price":4.5,"category":"Appetizer","available":true}'
```

If you still hit native OOM when building, try lowering `-Xmx` further (e.g. 128m) or closing other apps. Share the full console output if it still fails and I'll diagnose further.

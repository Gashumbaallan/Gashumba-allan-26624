# Student Registration API

A REST API for student registration and information management built with Spring Boot.

## Project Structure

```
question2-student-api/
├── src/
│   ├── main/
│   │   ├── java/question2/question2_student_api/
│   │   │   ├── controller/StudentController.java
│   │   │   ├── model/Student.java
│   │   │   ├── service/StudentService.java
│   │   │   └── Question2StudentApiApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/.../Question2StudentApiApplicationTests.java
└── pom.xml
```

## Student Model

The Student class has the following fields:
- `studentId` (Long) - Unique identifier
- `firstName` (String) - Student's first name
- `lastName` (String) - Student's last name
- `email` (String) - Student's email address
- `major` (String) - Field of study
- `gpa` (Double) - Grade Point Average

## API Endpoints

### 1. Get All Students
**GET** `/api/students`
- Returns a list of all students in the system
```json
[
  {
    "studentId": 1,
    "firstName": "John",
    "lastName": "Smith",
    "email": "john.smith@email.com",
    "major": "Computer Science",
    "gpa": 3.8
  },
  ...
]
```

### 2. Get Student by ID
**GET** `/api/students/{studentId}`
- Returns a specific student by their ID
```
GET /api/students/1
```

### 3. Get Students by Major
**GET** `/api/students/major/{major}`
- Returns all students in a specific major
```
GET /api/students/major/Computer%20Science
```

### 4. Filter Students by GPA
**GET** `/api/students/filter?gpa={minGpa}`
- Returns students with GPA greater than or equal to the specified minimum
```
GET /api/students/filter?gpa=3.5
```

### 5. Register a New Student
**POST** `/api/students`
- Creates and registers a new student
```json
Request Body:
{
  "firstName": "Robert",
  "lastName": "Taylor",
  "email": "robert.taylor@email.com",
  "major": "Engineering",
  "gpa": 3.7
}

Response: 201 Created
{
  "studentId": 6,
  "firstName": "Robert",
  "lastName": "Taylor",
  "email": "robert.taylor@email.com",
  "major": "Engineering",
  "gpa": 3.7
}
```

### 6. Update Student Information
**PUT** `/api/students/{studentId}`
- Updates an existing student's information
```json
Request:
PUT /api/students/1

Request Body:
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@email.com",
  "major": "Computer Science",
  "gpa": 3.95
}
```

## Sample Data

The application comes with 5 pre-loaded sample students:

1. **John Smith** - Computer Science, GPA: 3.8
2. **Sarah Johnson** - Computer Science, GPA: 3.9
3. **Michael Brown** - Mathematics, GPA: 3.5
4. **Emily Davis** - Physics, GPA: 3.2
5. **David Wilson** - Computer Science, GPA: 3.6

## Testing Scenarios

### Scenario 1: Create and List 5 Sample Students
```
GET /api/students
```
Expected: Returns all 5 sample students

### Scenario 2: Filter Students by Computer Science Major
```
GET /api/students/major/Computer Science
```
Expected: Returns John Smith, Sarah Johnson, and David Wilson (3 students)

### Scenario 3: Filter Students with GPA >= 3.5
```
GET /api/students/filter?gpa=3.5
```
Expected: Returns:
- John Smith (3.8)
- Sarah Johnson (3.9)
- Michael Brown (3.5)
- David Wilson (3.6)

Results excludes Emily Davis (3.2)

## Running the Application

### Build the Project
```bash
mvn clean package
```

### Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Run Tests
```bash
mvn test
```

## Example Usage with cURL

### Get all students
```bash
curl http://localhost:8080/api/students
```

### Get Computer Science students
```bash
curl "http://localhost:8080/api/students/major/Computer Science"
```

### Filter students with GPA >= 3.5
```bash
curl "http://localhost:8080/api/students/filter?gpa=3.5"
```

### Register a new student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Alice",
    "lastName": "Peterson",
    "email": "alice.p@email.com",
    "major": "Biology",
    "gpa": 3.4
  }'
```

### Update a student
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@email.com",
    "major": "Computer Science",
    "gpa": 3.9
  }'
```

## Requirements Met

✅ Creates a Student class with all required fields (studentId, firstName, lastName, email, major, gpa)
✅ Creates a StudentController with all 6 required endpoints
✅ Implements all filtering and retrieval operations
✅ Provides 5 sample students with different majors and GPAs
✅ Supports filtering students by major (Computer Science)
✅ Supports filtering students with GPA >= 3.5
✅ Includes comprehensive test scenarios

## Technologies Used

- Spring Boot 3.2.0
- Java 17
- Maven
- JUnit 5

package question2.question2_student_api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private List<Student> students = new ArrayList<>();

    public StudentController() {
        // Sample students
        students.add(new Student(1L, "John", "Doe", "john.doe@example.com", "Computer Science", 3.6));
        students.add(new Student(2L, "Jane", "Smith", "jane.smith@example.com", "Mathematics", 3.8));
        students.add(new Student(3L, "Alice", "Johnson", "alice.johnson@example.com", "Computer Science", 3.4));
        students.add(new Student(4L, "Bob", "Brown", "bob.brown@example.com", "Physics", 3.2));
        students.add(new Student(5L, "Charlie", "Davis", "charlie.davis@example.com", "Engineering", 3.9));
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        Optional<Student> student = students.stream().filter(s -> s.getStudentId().equals(studentId)).findFirst();
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/major/{major}")
    public List<Student> getStudentsByMajor(@PathVariable String major) {
        return students.stream().filter(s -> s.getMajor().equalsIgnoreCase(major)).toList();
    }

    @GetMapping("/filter")
    public List<Student> filterStudentsByGpa(@RequestParam Double minGpa) {
        return students.stream().filter(s -> s.getGpa() >= minGpa).toList();
    }

    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        students.add(student);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(studentId)) {
                students.set(i, student);
                return ResponseEntity.ok(student);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
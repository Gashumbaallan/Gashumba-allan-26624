package question2.question2_student_api.service;

import question2.question2_student_api.model.Student;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private Map<Long, Student> studentDatabase = new HashMap<>();
    private Long nextId = 1L;

    public StudentService() {
        initializeSampleData();
    }

    // Initialize sample data with 5 students with different majors and GPAs
    private void initializeSampleData() {
        addStudent(new Student(nextId++, "John", "Smith", "john.smith@email.com", "Computer Science", 3.8));
        addStudent(new Student(nextId++, "Sarah", "Johnson", "sarah.johnson@email.com", "Computer Science", 3.9));
        addStudent(new Student(nextId++, "Michael", "Brown", "michael.brown@email.com", "Mathematics", 3.5));
        addStudent(new Student(nextId++, "Emily", "Davis", "emily.davis@email.com", "Physics", 3.2));
        addStudent(new Student(nextId++, "David", "Wilson", "david.wilson@email.com", "Computer Science", 3.6));
    }

    // Get all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentDatabase.values());
    }

    // Get student by ID
    public Student getStudentById(Long studentId) {
        return studentDatabase.get(studentId);
    }

    // Get students by major
    public List<Student> getStudentsByMajor(String major) {
        return studentDatabase.values().stream()
                .filter(student -> student.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
    }

    // Filter students by minimum GPA
    public List<Student> filterStudentsByGpa(Double minGpa) {
        return studentDatabase.values().stream()
                .filter(student -> student.getGpa() >= minGpa)
                .collect(Collectors.toList());
    }

    // Register a new student
    public Student registerStudent(Student student) {
        student.setStudentId(nextId++);
        studentDatabase.put(student.getStudentId(), student);
        return student;
    }

    // Update student information
    public Student updateStudent(Long studentId, Student updatedStudent) {
        if (studentDatabase.containsKey(studentId)) {
            updatedStudent.setStudentId(studentId);
            studentDatabase.put(studentId, updatedStudent);
            return updatedStudent;
        }
        return null;
    }

    // Helper method to add student (for sample data)
    private void addStudent(Student student) {
        studentDatabase.put(student.getStudentId(), student);
    }

    // Delete a student (optional, useful for testing)
    public boolean deleteStudent(Long studentId) {
        return studentDatabase.remove(studentId) != null;
    }
}

package question2.question2_student_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import question2.question2_student_api.model.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Question2StudentApiApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String createUrl(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    public void testGetAllStudents() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                createUrl("/api/students"),
                Student[].class
        );
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 5, "Should have at least 5 sample students");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetStudentById() {
        ResponseEntity<Student> response = restTemplate.getForEntity(
                createUrl("/api/students/1"),
                Student.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getStudentId());
    }

    @Test
    public void testGetStudentsByMajor() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                createUrl("/api/students/major/Computer Science"),
                Student[].class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 3, "Should have at least 3 Computer Science students");
        for (Student student : response.getBody()) {
            assertEquals("Computer Science", student.getMajor());
        }
    }

    @Test
    public void testFilterStudentsByGpa() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                createUrl("/api/students/filter?gpa=3.5"),
                Student[].class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 3, "Should have at least 3 students with GPA >= 3.5");
        for (Student student : response.getBody()) {
            assertTrue(student.getGpa() >= 3.5, "GPA should be >= 3.5");
        }
    }

    @Test
    public void testRegisterNewStudent() {
        Student newStudent = new Student();
        newStudent.setFirstName("Robert");
        newStudent.setLastName("Taylor");
        newStudent.setEmail("robert.taylor@email.com");
        newStudent.setMajor("Engineering");
        newStudent.setGpa(3.7);

        ResponseEntity<Student> response = restTemplate.postForEntity(
                createUrl("/api/students"),
                newStudent,
                Student.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getStudentId());
        assertEquals("Robert", response.getBody().getFirstName());
    }

    @Test
    public void testUpdateStudent() {
        Student updatedStudent = new Student();
        updatedStudent.setFirstName("John");
        updatedStudent.setLastName("Doe");
        updatedStudent.setEmail("john.doe@email.com");
        updatedStudent.setMajor("Computer Science");
        updatedStudent.setGpa(3.95);

        restTemplate.put(
                createUrl("/api/students/1"),
                updatedStudent
        );

        ResponseEntity<Student> response = restTemplate.getForEntity(
                createUrl("/api/students/1"),
                Student.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Doe", response.getBody().getLastName());
    }

    @Test
    public void testFilterStudentsWithGpa3Point5() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                createUrl("/api/students/filter?gpa=3.5"),
                Student[].class
        );
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Student[] students = response.getBody();
        assertNotNull(students);
        
        // Verify all returned students have GPA >= 3.5
        for (Student student : students) {
            assertTrue(student.getGpa() >= 3.5, 
                    "Student " + student.getFirstName() + " has GPA " + student.getGpa() + " which is less than 3.5");
        }
        
        System.out.println("\n=== Students with GPA >= 3.5 ===");
        for (Student student : students) {
            System.out.println(student);
        }
    }
}

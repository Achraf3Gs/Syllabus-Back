package syllabus.example.syllabus.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.repositories.InstructorRepository;
import syllabus.example.syllabus.repositories.StudentRepository;
import syllabus.example.syllabus.entities.Student;
import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin(origins="*")
@RequestMapping({"/studentPilot"})
public class StudentController {

    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository, InstructorRepository instructorRepository) {
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
    }



    @GetMapping("/list")
    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    @PostMapping("/add/{instructorId}")
    Student createArticle(@PathVariable (value = "instructorId") Long instructorId,
                          @Valid @RequestBody Student student) {
        return instructorRepository.findById(instructorId).map(instructor -> {
            student.setInstructor(instructor);
            return studentRepository.save(student);
        }).orElseThrow(() -> new IllegalArgumentException("instructorId " + instructorId + " not found"));
    }

    @PutMapping("/update/{instructorId}/{studentId}")
    public Student updateStudentPilot(@PathVariable (value = "instructorId") Long instructorId,
                                      @PathVariable (value = "studentPilotId") Long studentId,
                                      @Valid @RequestBody Student studentRequest) {
        if(!instructorRepository.existsById(instructorId)) {
            throw new IllegalArgumentException("InstructorId " + instructorId + " not found");
        }

        return studentRepository.findById(studentId).map(student -> {
            student.setFirstName(studentRequest.getFirstName());
            student.setLastName(studentRequest.getLastName());
            student.setGrade(studentRequest.getGrade());
            student.setCallSign(studentRequest.getCallSign());
            return studentRepository.save(student);
        }).orElseThrow(() -> new IllegalArgumentException("StudentId " + studentId + "not found"));
    }

    @DeleteMapping("/delete/{studentPilotId}")
    public ResponseEntity<?> Student(@PathVariable (value = "studentId") Long studentId) {
        return studentRepository.findById(studentId).map(student -> {
            studentRepository.delete(student);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException("Student not found with id " + studentId));
    }

    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable Long studentId) {

        Optional<Student> p = studentRepository.findById(studentId);

        return p.get();

    }

    @GetMapping("/{instructorId}/students")
    public ResponseEntity<List<Student>> getStudentsByInstructorId(@PathVariable Long instructorId) {
        List<Student> students = studentRepository.findByInstructorId(instructorId);
        return ResponseEntity.ok(students);
    }
}


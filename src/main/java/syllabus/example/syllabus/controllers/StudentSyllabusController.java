package syllabus.example.syllabus.gradeSheet.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.entities.StudentSyllabus;
import syllabus.example.syllabus.repositories.StudentSyllabusRepository;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student-syllabus")
@CrossOrigin(origins = "*")
public class StudentSyllabusController {

    @Autowired
    private StudentSyllabusRepository studentSyllabusRepository;

    @GetMapping("/{id}")
    public StudentSyllabus getStudentSyllabus(@PathVariable Long id) {
        return studentSyllabusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StudentSyllabus not found with id: " + id));
    }

    @GetMapping("/student/{studentId}")
    public List<StudentSyllabus> getSyllabiForStudent(@PathVariable Long studentId) {
        return studentSyllabusRepository.findByStudentId(studentId);
    }

    @PostMapping("/add")
    public StudentSyllabus create(@Valid @RequestBody StudentSyllabus studentSyllabus) {
        return studentSyllabusRepository.save(studentSyllabus);
    }

    @GetMapping("/list")
    public List<StudentSyllabus> getAll() {
        return (List<StudentSyllabus>) studentSyllabusRepository.findAll();
    }
}

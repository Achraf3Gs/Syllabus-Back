package syllabus.example.syllabus.controllers;



import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.entities.StudentFlightDomainSyllabus;
import syllabus.example.syllabus.repositories.StudentFlightDomainSyllabusRepository;

import java.util.List;

@RestController
@RequestMapping("/student-flight-domain")
@CrossOrigin(origins = "*")
public class StudentFlightDomainSyllabusController {

    @Autowired
    private StudentFlightDomainSyllabusRepository studentFlightDomainSyllabusRepository;

    @GetMapping("/{id}")
    public StudentFlightDomainSyllabus getById(@PathVariable Long id) {
        return studentFlightDomainSyllabusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StudentFlightDomainSyllabus not found with id: " + id));
    }

    @GetMapping("/student-syllabus/{studentSyllabusId}")
    public List<StudentFlightDomainSyllabus> getByStudentSyllabus(@PathVariable Long studentSyllabusId) {
        return studentFlightDomainSyllabusRepository.findByStudentSyllabusId(studentSyllabusId);
    }

    @PostMapping("/add")
    public StudentFlightDomainSyllabus create(@Valid @RequestBody StudentFlightDomainSyllabus studentFlightDomain) {
        return studentFlightDomainSyllabusRepository.save(studentFlightDomain);
    }

    @GetMapping("/list")
    public List<StudentFlightDomainSyllabus> getAll() {
        return (List<StudentFlightDomainSyllabus>) studentFlightDomainSyllabusRepository.findAll();
    }
}

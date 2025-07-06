package syllabus.example.syllabus.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.entities.Instructor;
import syllabus.example.syllabus.repositories.InstructorRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/instructors","/hom*"})
@CrossOrigin(origins="*")
public class InstructorController {
    @Autowired
    private InstructorRepository instructorRepository;

    @GetMapping("/list")
    public List<syllabus.example.syllabus.entities.Instructor> getAllInstructors() {
        return (List<syllabus.example.syllabus.entities.Instructor>) instructorRepository.findAll();
    }

    @PostMapping("/add")
    public syllabus.example.syllabus.entities.Instructor createInstructor(@Valid @RequestBody syllabus.example.syllabus.entities.Instructor instructor) {
        return this.instructorRepository.save(instructor);
    }

    @PutMapping("/{instructorId}")
    public syllabus.example.syllabus.entities.Instructor updateInstructor(@PathVariable Long instructorId, @Valid @RequestBody syllabus.example.syllabus.entities.Instructor instructorRequest) {
        return instructorRepository.findById(instructorId).map(instructor -> {
            instructor.setFirstName(instructorRequest.getFirstName());
            instructor.setLastName(instructorRequest.getLastName());
            instructor.setCallSign(instructorRequest.getCallSign());
            instructor.setGrade(instructorRequest.getGrade());
            return this.instructorRepository.save(instructor);
        }).orElseThrow(() -> new IllegalArgumentException("InstructorId " + instructorId + " not found"));
    }


    @DeleteMapping("/{instructorId}")
    public syllabus.example.syllabus.entities.Instructor deleteInstructor(@PathVariable Long instructorId) {
        return instructorRepository.findById(instructorId).map(instructor -> {
            this.instructorRepository.delete(instructor);
            // return ResponseEntity.ok().build();
            return instructor;
        }).orElseThrow(() -> new IllegalArgumentException("InstructorId " + instructorId + " not found"));
    }

    @GetMapping("/{instructorId}")
    public syllabus.example.syllabus.entities.Instructor getInstructor(@PathVariable Long instructorId) {

        Optional<syllabus.example.syllabus.entities.Instructor> p = instructorRepository.findById(instructorId);

        return p.get();

    }
    @GetMapping("/callSign/{callSign}")
    public Instructor getInstructorByCallSign(@PathVariable Long callSign) {
        Optional<Instructor> instructor = instructorRepository.findByCallSign(callSign);

        return instructor.orElseThrow(() ->
                new RuntimeException("Instructor not found with callSign: " + callSign));
    }
}
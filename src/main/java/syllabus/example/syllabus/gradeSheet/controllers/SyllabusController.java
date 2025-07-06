package syllabus.example.syllabus.gradeSheet.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.gradeSheet.entities.FlightDomain;
import syllabus.example.syllabus.gradeSheet.entities.FlightDomainSyllabus;
import syllabus.example.syllabus.gradeSheet.entities.Phase;
import syllabus.example.syllabus.gradeSheet.entities.Syllabus;
import syllabus.example.syllabus.gradeSheet.repository.SyllabusRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/syllabus"})
@CrossOrigin(origins="*")
public class SyllabusController {

    @Autowired
    private SyllabusRepository syllabusRepository;

    @GetMapping("/{syllabusId}")
    public syllabus.example.syllabus.gradeSheet.entities.Syllabus getSyllabus(@PathVariable Long syllabusId) {

        Optional<Syllabus> p = syllabusRepository.findById(syllabusId);


        return p.get();

    }
    @GetMapping("/title/{title}")
    public Syllabus getSyllabusByTitle(@PathVariable String title) {
        Optional<Syllabus> syllabus = syllabusRepository.findByTitle(title);

        return syllabus.orElseThrow(() ->
                new RuntimeException("Syllabus not found with name: " + title));
    }

    @PostMapping("/add")
    public syllabus.example.syllabus.gradeSheet.entities.Syllabus createSyllabus(@Valid @RequestBody syllabus.example.syllabus.gradeSheet.entities.Syllabus syllabus) {
        return this.syllabusRepository.save(syllabus);
    }

    @GetMapping("/list")
    public List<Syllabus> getAllSyllabus() {

        return (List<Syllabus>) syllabusRepository.findAll();
    }

    @PutMapping("/update/{syllabusId}")
    public Syllabus updateSyllabus(@PathVariable Long syllabusId, @Valid  @RequestBody Syllabus syllabusRequest) {
        return syllabusRepository.findById(syllabusId).map(syllabus -> {
            syllabus.setTitle(syllabusRequest.getTitle());

            return this.syllabusRepository.save(syllabus);
        }).orElseThrow(() -> new IllegalArgumentException("syllabusId " + syllabusId + " not found"));
    }

    @DeleteMapping("/delete/{syllabusId}")
    public ResponseEntity<?> Syllabus(@PathVariable (value = "syllabusId") Long syllabusId) {
        return syllabusRepository.findById(syllabusId).map(syllabus -> {
            syllabusRepository.delete(syllabus);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException("syllabus not found with id " + syllabusId));
    }

}

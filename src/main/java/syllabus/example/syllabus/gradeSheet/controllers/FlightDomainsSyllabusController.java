package syllabus.example.syllabus.gradeSheet.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.entities.Student;
import syllabus.example.syllabus.gradeSheet.entities.FlightDomain;
import syllabus.example.syllabus.gradeSheet.entities.FlightDomainSyllabus;
import syllabus.example.syllabus.gradeSheet.repository.FlightDomainRepository;
import syllabus.example.syllabus.gradeSheet.repository.FlightDomainSyllabusRepository;
import syllabus.example.syllabus.gradeSheet.repository.SyllabusRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/flightDomainSyllabus"})
@CrossOrigin(origins="*")
public class FlightDomainsSyllabusController {
    @Autowired
    private FlightDomainSyllabusRepository flightDomainSyllabusRepository;

    @Autowired
    private SyllabusRepository syllabusRepository;

    @GetMapping("/list")
    public List<FlightDomainSyllabus> getAllFlightDomainSyllabus() {
        return flightDomainSyllabusRepository.findAll();
    }



    @GetMapping("/{flightDomainSyllabusId}")
    public FlightDomainSyllabus getFlightDomainSyllabus(@PathVariable Long flightDomainSyllabusId) {

        Optional<FlightDomainSyllabus> p = flightDomainSyllabusRepository.findById(flightDomainSyllabusId);

        return p.get();

    }



    @PostMapping("/add/{syllabusId}")
    FlightDomainSyllabus createArticle(@PathVariable (value = "syllabusId") Long syllabusId,
                                       @Valid @RequestBody FlightDomainSyllabus flightDomainSyllabus) {
        return syllabusRepository.findById(syllabusId).map(syllabus -> {
            flightDomainSyllabus.setSyllabus(syllabus);
            return flightDomainSyllabusRepository.save(flightDomainSyllabus);
        }).orElseThrow(() -> new IllegalArgumentException("syllabusId " + syllabusId + " not found"));
    }

    @PutMapping("/update/{syllabusId}/{flightDomainSyllabusId}")
    public FlightDomainSyllabus updateFlightDomainSyllabus(@PathVariable (value = "syllabusId") Long syllabusId,
                                                           @PathVariable (value = "flightDomainSyllabusId") Long flightDomainSyllabusId,
                                                           @Valid @RequestBody FlightDomainSyllabus flightDomainSyllabusRequest) {
        if(!syllabusRepository.existsById(syllabusId)) {
            throw new IllegalArgumentException("SyllabusId " + syllabusId + " not found");
        }

        return flightDomainSyllabusRepository.findById(flightDomainSyllabusId).map(flightDomainSyllabus -> {
            flightDomainSyllabus.setName(flightDomainSyllabusRequest.getName());
            flightDomainSyllabus.setBlock(flightDomainSyllabusRequest.getBlock());
            return flightDomainSyllabusRepository.save(flightDomainSyllabus);
        }).orElseThrow(() -> new IllegalArgumentException("flightDomainSyllabusId " + flightDomainSyllabusId + "not found"));
    }

    @GetMapping("/name/{name}")
    public FlightDomainSyllabus getFlightDomainSyllabusByName(@PathVariable String name) {
        Optional<FlightDomainSyllabus> flightDomainSyllabus = flightDomainSyllabusRepository.findByName(name);

        return flightDomainSyllabus.orElseThrow(() ->
                new RuntimeException("flightDomainSyllabus not found with name: " + name));
    }

    @DeleteMapping("/delete/{flightDomainSyllabusId}")
    public ResponseEntity<?> FlightDomainSyllabus(@PathVariable (value = "flightDomainSyllabusId") Long flightDomainSyllabusId) {
        return flightDomainSyllabusRepository.findById(flightDomainSyllabusId).map(flightDomainSyllabus -> {
            flightDomainSyllabusRepository.delete(flightDomainSyllabus);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException("flightDomainSyllabus not found with id " + flightDomainSyllabusId));
    }

    @GetMapping("/{syllabusId}/flightDomainSyllabuss")
    public ResponseEntity<List<FlightDomainSyllabus>> getFlightDomainSyllabussBySyllabusId(@PathVariable Long syllabusId) {
        List<FlightDomainSyllabus> flightDomainSyllabuss = flightDomainSyllabusRepository.findBySyllabusId(syllabusId);
        return ResponseEntity.ok(flightDomainSyllabuss);
    }

}

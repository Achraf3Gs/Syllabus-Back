package syllabus.example.syllabus.gradeSheet.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.gradeSheet.entities.FlightDomain;
import syllabus.example.syllabus.gradeSheet.entities.Phase;
import syllabus.example.syllabus.gradeSheet.repository.PhaseRepository;

import java.util.List;

@RestController
@RequestMapping({"/phase"})
@CrossOrigin(origins="*")
public class PhaseController {

    @Autowired
    private PhaseRepository phaseRepository;

    @GetMapping("/list")
    public List<Phase> getAllPhase() {

        return (List<Phase>) phaseRepository.findAll();
    }


    @PostMapping("/add")
    public Phase createPhase(@Valid @RequestBody Phase phase) {
        return this.phaseRepository.save(phase);
    }

    @DeleteMapping("/delete/{phaseId}")
    public ResponseEntity<?> Phase(@PathVariable (value = "phaseId") Long phaseId) {
        return phaseRepository.findById(phaseId).map(phase -> {
            phaseRepository.delete(phase);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException("phase not found with id " + phaseId));
    }

    @PutMapping("/update/{phaseId}")
    public Phase updatePhase(@PathVariable Long phaseId, @Valid  @RequestBody Phase phaseRequest) {
        return phaseRepository.findById(phaseId).map(phase -> {
            phase.setPhaseName(phaseRequest.getPhaseName());

            return this.phaseRepository.save(phase);
        }).orElseThrow(() -> new IllegalArgumentException("phaseId " + phaseId + " not found"));
    }
}

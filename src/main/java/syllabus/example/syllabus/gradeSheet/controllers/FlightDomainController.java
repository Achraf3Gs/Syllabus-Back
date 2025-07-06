package syllabus.example.syllabus.gradeSheet.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.gradeSheet.entities.FlightDomain;
import syllabus.example.syllabus.gradeSheet.repository.FlightDomainRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/flightDomain"})
@CrossOrigin(origins="*")
public class FlightDomainController {
    @Autowired
    private FlightDomainRepository flightDomainRepository;

    @GetMapping("/list")
    public List<FlightDomain> getAllFlightDomain() {

        return (List<FlightDomain>) flightDomainRepository.findAll();
    }


    @GetMapping("/{flightDomainId}")
    public FlightDomain getFlightDomain(@PathVariable Long flightDomainId) {

        Optional<FlightDomain> p = flightDomainRepository.findById(flightDomainId);

        return p.get();

    }

    @PostMapping("/add")
    public FlightDomain createFlightDomain(@Valid @RequestBody FlightDomain flightDomain) {
        return this.flightDomainRepository.save(flightDomain);
    }

    @GetMapping("/name/{name}")
    public FlightDomain getFlightDomainByName(@PathVariable String name) {
        Optional<FlightDomain> flightDomain = flightDomainRepository.findByName(name);

        return flightDomain.orElseThrow(() ->
                new RuntimeException("flightDomain not found with name: " + name));
    }

    @PutMapping("/update/{flightDomainId}")
    public FlightDomain updateFlightDomain(@PathVariable Long flightDomainId, @Valid  @RequestBody FlightDomain flightDomainRequest) {
        return flightDomainRepository.findById(flightDomainId).map(flightDomain -> {
            flightDomain.setName(flightDomainRequest.getName());

            return this.flightDomainRepository.save(flightDomain);
        }).orElseThrow(() -> new IllegalArgumentException("flightDomainId " + flightDomainId + " not found"));
    }

    @DeleteMapping("/delete/{flightDomainId}")
    public ResponseEntity<?> FlightDomain(@PathVariable (value = "flightDomainId") Long flightDomainId) {
        return flightDomainRepository.findById(flightDomainId).map(flightDomain -> {
            flightDomainRepository.delete(flightDomain);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException("flightDomain not found with id " + flightDomainId));
    }

}

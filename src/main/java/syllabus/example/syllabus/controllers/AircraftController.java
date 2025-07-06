package syllabus.example.syllabus.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.entities.Aircraft;
import syllabus.example.syllabus.entities.Instructor;
import syllabus.example.syllabus.repositories.AircraftRepository;
import syllabus.example.syllabus.repositories.InstructorRepository;

import java.util.List;

@RestController
@RequestMapping({"/aircraft"})
@CrossOrigin(origins="*")
public class AircraftController {

    @Autowired
    private AircraftRepository AircraftRepository;

    @GetMapping("/list")
    public List<Aircraft> getAllAircraft() {
        return (List<syllabus.example.syllabus.entities.Aircraft>) AircraftRepository.findAll();
    }

    @PostMapping("/add")
    public syllabus.example.syllabus.entities.Aircraft createAircraft(@Valid @RequestBody syllabus.example.syllabus.entities.Aircraft aircraft) {
        return this.AircraftRepository.save(aircraft);
    }
    @PutMapping("/{aircraftId}")
    public syllabus.example.syllabus.entities.Aircraft updateAircraft(@PathVariable Long aircraftId, @Valid @RequestBody syllabus.example.syllabus.entities.Aircraft aircraftRequest) {
        return AircraftRepository.findById(aircraftId).map(aircraft -> {
            aircraft.setRegistration(aircraftRequest.getRegistration());
            aircraft.setAvailability(aircraftRequest.getAvailability());
            aircraft.setCauses(aircraftRequest.getCauses());
            return this.AircraftRepository.save(aircraft);
        }).orElseThrow(() -> new IllegalArgumentException("AircraftId " + aircraftId + " not found"));
    }
}

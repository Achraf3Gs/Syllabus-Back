package syllabus.example.syllabus.gradeSheet.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.gradeSheet.entities.FlightDomain;
import syllabus.example.syllabus.gradeSheet.entities.ManeuverItem;
import syllabus.example.syllabus.gradeSheet.repository.FlightDomainRepository;
import syllabus.example.syllabus.gradeSheet.repository.ManeuverItemRepository;

import java.util.List;

@RestController
@RequestMapping("/maneuverItem")
@CrossOrigin(origins = "*")
public class ManeuverItemController {

    @Autowired
    private ManeuverItemRepository maneuverItemRepository;

    @Autowired
    private FlightDomainRepository flightDomainRepository;

    // Get all maneuver items
    @GetMapping("/list")
    public List<ManeuverItem> getAllManeuverItems() {
        return (List<ManeuverItem>)maneuverItemRepository.findAll();
    }

    // Get a single maneuver item by ID
    @GetMapping("/{maneuverItemId}")
    public ManeuverItem getManeuverItem(@PathVariable Long maneuverItemId) {
        return maneuverItemRepository.findById(maneuverItemId)
                .orElseThrow(() -> new IllegalArgumentException("ManeuverItem with ID " + maneuverItemId + " not found"));
    }

    // Create a maneuver item and assign it to a maneuver group
    @PostMapping("/add/{flightDomainId}")
    public ManeuverItem createManeuverItem(
            @PathVariable Long flightDomainId,
            @Valid @RequestBody ManeuverItem maneuverItem) {

        FlightDomain group = flightDomainRepository.findById(flightDomainId)
                .orElseThrow(() -> new IllegalArgumentException("flightDomain with ID " + flightDomainId + " not found"));

        maneuverItem.setFlightDomain(group);
        return maneuverItemRepository.save(maneuverItem);
    }

    @PutMapping("/update/{flightDomainId}/{maneuverItemId}")
    public ManeuverItem updateManeuverItem(@PathVariable (value = "flightDomainId") Long flightDomainId,
                                      @PathVariable (value = "maneuverItemId") Long maneuverItemId,
                                      @Valid @RequestBody ManeuverItem maneuverItemRequest) {
        if(!flightDomainRepository.existsById(flightDomainId)) {
            throw new IllegalArgumentException("flightDomainId " + flightDomainId + " not found");
        }

        return maneuverItemRepository.findById(maneuverItemId).map(maneuverItem -> {
            maneuverItem.setName(maneuverItemRequest.getName());
            maneuverItem.setCts(maneuverItemRequest.getCts());

            return maneuverItemRepository.save(maneuverItem);
        }).orElseThrow(() -> new IllegalArgumentException("ManeuverItemId " + maneuverItemId + "not found"));
    }

    @DeleteMapping("/delete/{maneuverItemId}")
    public ResponseEntity<?> ManeuverItem(@PathVariable (value = "maneuverItemId") Long maneuverItemId) {
        return maneuverItemRepository.findById(maneuverItemId).map(maneuverItem -> {
            maneuverItemRepository.delete(maneuverItem);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException("maneuverItem not found with id " + maneuverItemId));
    }
}

package syllabus.example.syllabus.gradeSheet.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.entities.Student;
import syllabus.example.syllabus.entities.StudentFlightDomainSyllabus;

import syllabus.example.syllabus.gradeSheet.entities.*;
import syllabus.example.syllabus.gradeSheet.repository.FlightDomainSyllabusRepository;
import syllabus.example.syllabus.gradeSheet.repository.GradeSheetManeuverItemRepository;
import syllabus.example.syllabus.gradeSheet.repository.GradeSheetRepository;
import syllabus.example.syllabus.gradeSheet.repository.ManeuverItemRepository;
import syllabus.example.syllabus.repositories.StudentFlightDomainSyllabusRepository;


import java.util.List;

@RestController
@RequestMapping("/gradeSheet")
@CrossOrigin(origins = "*")
public class GradeSheetController {

    @Autowired
    private GradeSheetRepository gradeSheetRepository;

    @Autowired
    private ManeuverItemRepository maneuverItemRepository;

    @Autowired
    private GradeSheetManeuverItemRepository gradeSheetManeuverItemRepository;

    @Autowired
    private FlightDomainSyllabusRepository flightDomainSyllabusRepository;

    // ✅ Get GradeSheet by ID
    @GetMapping("/{gradeSheetId}")
    public GradeSheet getGradeSheet(@PathVariable Long gradeSheetId) {
        return gradeSheetRepository.findById(gradeSheetId)
                .orElseThrow(() -> new RuntimeException("GradeSheet not found with id: " + gradeSheetId));
    }

    // ✅ Basic Add GradeSheet
    @PostMapping("/add")
    public GradeSheet createGradeSheet(@Valid @RequestBody GradeSheet gradeSheet) {
        return gradeSheetRepository.save(gradeSheet);
    }

    @PostMapping("/addTemplateWithManeuvers/{flightDomainSyllabusId}")
    public ResponseEntity<GradeSheet> createGradeSheetTemplate(
            @PathVariable Long flightDomainSyllabusId,
            @RequestBody GradeSheetRequest request
    ) {
        FlightDomainSyllabus templateFDS = flightDomainSyllabusRepository
                .findById(flightDomainSyllabusId)
                .orElseThrow(() -> new RuntimeException("FlightDomainSyllabus not found"));

        // 2. Build GradeSheet
        GradeSheet gradeSheet = new GradeSheet();
        gradeSheet.setName(request.getName());
        gradeSheet.setBlock(request.getBlock());
        gradeSheet.setSortieType(request.getSortieType());
        gradeSheet.setSortieNbr(request.getSortieNbr());
        gradeSheet.setPhase(request.getPhase());
        gradeSheet.setMixDur(request.getMixDur());
        gradeSheet.setCrewMember(request.getCrewMember());
        gradeSheet.setStudent(request.getStudent());
        gradeSheet.setAircraft(request.getAircraft());
        gradeSheet.setDate(request.getDate());
        gradeSheet.setSelectedGroupId(request.getSelectedGroupId());
        gradeSheet.setInstructorName(request.getInstructorName());
        gradeSheet.setOverallGrade(null); // Template, no grade
        gradeSheet.setFlightDomainSyllabus(templateFDS);


        // Set the template FlightDomainSyllabus reference
        gradeSheet.setStudentFlightDomainSyllabus(null);

        // Save the GradeSheet template
        gradeSheet = gradeSheetRepository.save(gradeSheet);




        // 3. Add maneuver items with MIF only
        for (GradeSheetRequest.ManeuverEntry entry : request.getManeuverItems()) {
            ManeuverItem item = maneuverItemRepository.findById(entry.getId())
                    .orElseThrow(() -> new RuntimeException("ManeuverItem not found: " + entry.getId()));

            GradeSheetManeuverItem gsItem = GradeSheetManeuverItem.builder()
                    .gradeSheet(gradeSheet)
                    .maneuverItem(item)
                    .mifRequirement(entry.getMifRequirement())
                    .rating(null) // Template — no rating
                    .build();

            gradeSheetManeuverItemRepository.save(gsItem);
        }

        return ResponseEntity.ok(gradeSheet);
    }


    // ✅ Get all GradeSheets by StudentFlightDomainSyllabus
    @GetMapping("/{flightDomainSyllabusId}/flightDomainGradeSheets")
    public ResponseEntity<List<GradeSheet>> getFlightDomainGradeSheetsByFlightDomainSyllabusId(
            @PathVariable Long flightDomainSyllabusId) {
        List<GradeSheet> gradeSheets = gradeSheetRepository.findByFlightDomainSyllabusId(flightDomainSyllabusId);
        return ResponseEntity.ok(gradeSheets);
    }

    // ✅ Get all GradeSheets by StudentFlightDomainSyllabus
    @GetMapping("/{studentFlightDomainSyllabusId}/student-flightDomainGradeSheets")
    public ResponseEntity<List<GradeSheet>> getFlightDomainGradeSheetsByStudentFlightDomainSyllabusId(
            @PathVariable Long studentFlightDomainSyllabusId) {
        List<GradeSheet> gradeSheets = gradeSheetRepository.findByStudentFlightDomainSyllabusId(studentFlightDomainSyllabusId);
        return ResponseEntity.ok(gradeSheets);
    }


    @DeleteMapping("/delete/{gradeSheetId}")
    public ResponseEntity<?> GradeSheet(@PathVariable (value = "gradeSheetId") Long gradeSheetId) {
        return gradeSheetRepository.findById(gradeSheetId).map(gradeSheet -> {
            gradeSheetRepository.delete(gradeSheet);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException("GradeSheet not found with id " + gradeSheetId));
    }

    @Transactional
    @PutMapping("/update/{flightDomainSyllabusId}/{gradeSheetId}")
    public ResponseEntity<GradeSheet> updateGradeSheet(
            @PathVariable Long flightDomainSyllabusId,
            @PathVariable Long gradeSheetId,
            @Valid @RequestBody GradeSheetRequest request) {

        // Check if the FlightDomainSyllabus exists
        FlightDomainSyllabus fds = flightDomainSyllabusRepository.findById(flightDomainSyllabusId)
                .orElseThrow(() -> new RuntimeException("FlightDomainSyllabus not found"));

        // Find existing GradeSheet
        GradeSheet existingGradeSheet = gradeSheetRepository.findById(gradeSheetId)
                .orElseThrow(() -> new RuntimeException("GradeSheet not found"));

        // Update GradeSheet fields
        existingGradeSheet.setName(request.getName());
        existingGradeSheet.setBlock(request.getBlock());
        existingGradeSheet.setSortieType(request.getSortieType());
        existingGradeSheet.setSortieNbr(request.getSortieNbr());
        existingGradeSheet.setPhase(request.getPhase());
        existingGradeSheet.setMixDur(request.getMixDur());
        existingGradeSheet.setCrewMember(request.getCrewMember());
        existingGradeSheet.setStudent(request.getStudent());
        existingGradeSheet.setAircraft(request.getAircraft());
        existingGradeSheet.setDate(request.getDate());
        existingGradeSheet.setSelectedGroupId(request.getSelectedGroupId());
        existingGradeSheet.setInstructorName(request.getInstructorName());
        existingGradeSheet.setFlightDomainSyllabus(fds);
        existingGradeSheet.setStudentFlightDomainSyllabus(null); // keep null if it's a template
        existingGradeSheet.setOverallGrade(null); // still a template

        // Save updated GradeSheet
        existingGradeSheet = gradeSheetRepository.save(existingGradeSheet);

        // Remove old maneuver items linked to this GradeSheet
        gradeSheetManeuverItemRepository.deleteByGradeSheet(existingGradeSheet);

        // Add updated maneuver items
        for (GradeSheetRequest.ManeuverEntry entry : request.getManeuverItems()) {
            ManeuverItem item = maneuverItemRepository.findById(entry.getId())
                    .orElseThrow(() -> new RuntimeException("ManeuverItem not found: " + entry.getId()));

            GradeSheetManeuverItem gsItem = GradeSheetManeuverItem.builder()
                    .gradeSheet(existingGradeSheet)
                    .maneuverItem(item)
                    .mifRequirement(entry.getMifRequirement())
                    .rating(null)
                    .build();

            gradeSheetManeuverItemRepository.save(gsItem);
        }

        return ResponseEntity.ok(existingGradeSheet);
    }

}

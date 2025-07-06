package syllabus.example.syllabus.gradeSheet.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.gradeSheet.entities.GradeSheetManeuverItem;
import syllabus.example.syllabus.gradeSheet.entities.ManeuverWithMifDTO;
import syllabus.example.syllabus.gradeSheet.repository.GradeSheetManeuverItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/gradeSheetManeuverItem"})
@CrossOrigin(origins="*")
public class GradeSheetManeuverItemController {


    @Autowired
    private  GradeSheetManeuverItemRepository repository;

    @GetMapping("/maneuversWithMif/{gradeSheetId}")
    public ResponseEntity<List<ManeuverWithMifDTO>> getManeuversWithMif(@PathVariable Long gradeSheetId) {
        List<GradeSheetManeuverItem> items = repository.findFullWithManeuverByGradeSheetId(gradeSheetId);

        List<ManeuverWithMifDTO> result = items.stream()
                .map(item -> new ManeuverWithMifDTO(
                        item.getManeuverItem().getId(),
                        item.getManeuverItem().getName(),
                        item.getMifRequirement(),
                        item.getManeuverItem().getCts()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
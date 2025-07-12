package syllabus.example.syllabus.gradeSheet.controllers;

import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import syllabus.example.syllabus.entities.AssignSyllabusRequest;
import syllabus.example.syllabus.entities.StudentSyllabus;
import syllabus.example.syllabus.entities.StudentSyllabusDTO;
import syllabus.example.syllabus.entities.StudentSyllabusService;
import syllabus.example.syllabus.repositories.StudentSyllabusRepository;


import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/studentPilot/{studentId}/syllabi")
public class StudentSyllabusController {

    @Autowired
    private StudentSyllabusService studentSyllabusService;

    @PostMapping
    public ResponseEntity<StudentSyllabus> assignSyllabus(
            @PathVariable Long studentId,
            @Valid @RequestBody AssignSyllabusRequest request) throws BadRequestException {

        StudentSyllabus assignment = studentSyllabusService.assignSyllabus(studentId, request.getSyllabusId());
        return ResponseEntity.ok(assignment);
    }

    // ✅ New GET endpoint
    @GetMapping
    public ResponseEntity<List<StudentSyllabusDTO>> getSyllabiForStudent(@PathVariable Long studentId) {
        List<StudentSyllabusDTO> syllabi = studentSyllabusService.getSyllabiForStudent(studentId);
        return ResponseEntity.ok(syllabi);
    }

}

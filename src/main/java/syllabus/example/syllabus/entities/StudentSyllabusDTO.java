package syllabus.example.syllabus.entities;

import java.time.LocalDateTime;

public record StudentSyllabusDTO(
        Long id,
        Long syllabusId,
        String syllabusName,
        LocalDateTime assignedDate,
        String status
) {}


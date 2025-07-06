package syllabus.example.syllabus.gradeSheet.entities;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ManeuverWithMifDTO {
    private Long id;
    private String name;
    private String mifRequirement;
    private Cts cts;
}

package syllabus.example.syllabus.gradeSheet.entities;

import lombok.Data;

import java.util.List;

@Data
public class GradeSheetRequest {
    private String name;
    private String block;
    private String sortieType;
    private String sortieNbr;
    private String phase;
    private String mixDur;
    private String crewMember;
    private String student;
    private String aircraft;
    private String date;
    private Long selectedGroupId;
    private String instructorName;
    private String overallGrade;

    private Long studentFlightDomainSyllabusId; // This refers to the template FDS

    private List<ManeuverEntry> maneuverItems;

    @Data
    public static class ManeuverEntry {
        private Long id;               // ManeuverItem ID
        private String name;           // Optional
        private String mifRequirement; // Required
        private String rating;         // null for template
    }
}

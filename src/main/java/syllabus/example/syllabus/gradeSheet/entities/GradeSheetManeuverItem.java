package syllabus.example.syllabus.gradeSheet.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import syllabus.example.syllabus.entities.Instructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class GradeSheetManeuverItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "grade_sheet_id", nullable = false)
    @JsonBackReference
    private GradeSheet gradeSheet;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "maneuver_item_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ManeuverItem maneuverItem;


    // Custom per-grade-sheet values
    private String rating; // E, G, F, U, etc.

    // Manual MIF value set by user for this specific maneuver in this grade sheet
    @Column(name = "mif_requirement")
    private String mifRequirement; // This will be manually set by the user




}
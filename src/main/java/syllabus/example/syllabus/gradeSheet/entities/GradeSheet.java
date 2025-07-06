package syllabus.example.syllabus.gradeSheet.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import syllabus.example.syllabus.entities.StudentFlightDomainSyllabus;
import syllabus.example.syllabus.gradeSheet.enums.SortieRemark;
import syllabus.example.syllabus.gradeSheet.enums.SortieStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GradeSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // your fields...
    private String name;
    private String block;
    private String mixDur;
    private String crewMember;
    private String student;
    private String aircraft;
    private String date;
    private Long selectedGroupId;
    private String instructorName;
    private String sortieType;
    private String sortieNbr;
    private String overallGrade;
    @Column(name = "Phase")
    private String phase;

    @OneToMany(mappedBy = "gradeSheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    private SortieStatus sortieStatus;

    @ElementCollection
    private List<SortieRemark> sortieRemarks;

    private Integer ils;
    private Integer overheadPattern;
    private Integer vor;
    private Integer missedApproach;
    private Integer rnav;
    private Integer landings;

    // ✅ FIXED: ManyToOne to FlightDomainSyllabus
    @ManyToOne
    @JoinColumn(name = "flight_domain_syllabus_id")
    private FlightDomainSyllabus flightDomainSyllabus;

    @ManyToOne
    @JoinColumn(name = "student_flight_domain_id")
    private StudentFlightDomainSyllabus studentFlightDomainSyllabus;

    @OneToMany(mappedBy = "gradeSheet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<GradeSheetManeuverItem> gradeSheetManeuverItems;

}

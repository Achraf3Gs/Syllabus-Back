package syllabus.example.syllabus.entities;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import syllabus.example.syllabus.gradeSheet.entities.FlightDomainSyllabus;
import syllabus.example.syllabus.gradeSheet.entities.GradeSheet;
import syllabus.example.syllabus.gradeSheet.entities.Syllabus;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentFlightDomainSyllabus {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private FlightDomainSyllabus flightDomainSyllabus; // Template block

    @ManyToOne
    private StudentSyllabus studentSyllabus;

    @OneToMany(mappedBy = "studentFlightDomainSyllabus", cascade = CascadeType.ALL)
    private List<GradeSheet> gradeSheets;
}


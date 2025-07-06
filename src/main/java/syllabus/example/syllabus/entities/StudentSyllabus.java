package syllabus.example.syllabus.entities;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import syllabus.example.syllabus.gradeSheet.entities.Syllabus;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentSyllabus {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Syllabus syllabus; // This links to your current template

    @OneToMany(mappedBy = "studentSyllabus", cascade = CascadeType.ALL)
    private List<StudentFlightDomainSyllabus> studentFlightDomains;
}


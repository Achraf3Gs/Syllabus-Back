package syllabus.example.syllabus.gradeSheet.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import syllabus.example.syllabus.entities.Student;
import syllabus.example.syllabus.entities.StudentSyllabus;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Title is mandatory")
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "syllabus", cascade = CascadeType.ALL)
    private List<StudentSyllabus> studentSyllabus;


}

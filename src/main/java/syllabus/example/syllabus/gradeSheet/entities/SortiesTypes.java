package syllabus.example.syllabus.gradeSheet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sortiesTypes")
public class SortiesTypes {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "sortiesTypes_name", unique = true)
    private String sortiesTypeName;
    // Replace single MIF with embedded MIF values

}

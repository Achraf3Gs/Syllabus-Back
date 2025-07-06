package syllabus.example.syllabus.gradeSheet.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ManeuverItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "ManeuverName")
    private String name;




    @ManyToOne
    @JoinColumn(name = "flight_domain_id")
    @JsonBackReference
    private FlightDomain flightDomain;

    // Embedded Cts field containing grading criteria
    @Embedded
    private Cts cts;
}
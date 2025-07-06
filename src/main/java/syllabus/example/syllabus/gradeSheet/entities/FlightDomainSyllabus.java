package syllabus.example.syllabus.gradeSheet.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flightDomainSyllabus")

public class FlightDomainSyllabus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "flightDomainSyllabus_number", unique = true)
    private String name;
    // Replace single MIF with embedded MIF values

    @Column(name = "flightDomainSyllabus_block_number")
    private String block;




    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "esyllabus_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Syllabus syllabus;
}

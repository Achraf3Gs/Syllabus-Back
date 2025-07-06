package syllabus.example.syllabus.gradeSheet.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FlightDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "flightDomain", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ManeuverItem> maneuverItems;




}

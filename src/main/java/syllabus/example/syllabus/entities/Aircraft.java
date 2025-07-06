package syllabus.example.syllabus.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "aircraft registration is mandatory")
    @Column(name = "registration")
    private String registration;

    @NotBlank(message = "Availability is mandatory")
    @Column(name = "availability")
    private Boolean availability;

    @NotBlank(message = "Causes is mandatory")
    @Column(name = "causes")
    private String causes;
}

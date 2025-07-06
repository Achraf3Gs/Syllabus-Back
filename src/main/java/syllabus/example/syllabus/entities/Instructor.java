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

public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "FirstName is mandatory")
    @Column(name = "firstName")
    private String firstName;

    @NotBlank(message = "LastName is mandatory")
    @Column(name = "LastName")
    private String lastName;

    @NotBlank(message = "Grade is mandatory")
    @Column(name = "grade")
    private String grade;


    @NotBlank(message = "CallSign is mandatory")
    @Column(name = "CallSign")
    private Long callSign;

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getGrade() {
        return grade;
    }

    public void setCallSign(Long callSign) {
        this.callSign = callSign;
    }
    public Long getCallSign() {
        return callSign;
    }


}

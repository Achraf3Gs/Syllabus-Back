package syllabus.example.syllabus.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import syllabus.example.syllabus.gradeSheet.entities.Syllabus;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity


public class Student {
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

    /**** Many To One ****/

    /****
     *
     FetchType.LAZY = Doesn’t load the relationships unless explicitly “asked for” via getter
     FetchType.EAGER = Loads ALL relationships
     */

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "instructor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Instructor instructor;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentSyllabus> studentSyllabus;

    public Instructor getInstructor() {
        return instructor;
    }
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
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

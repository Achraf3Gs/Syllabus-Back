package syllabus.example.syllabus.gradeSheet.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phrase;

    // Optional: link comment to GradeSheet
    @ManyToOne
    @JoinColumn(name = "grade_sheet_id")
    private GradeSheet gradeSheet;

    public Comment() {}

    public Comment(String phrase) {
        this.phrase = phrase;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public GradeSheet getGradeSheet() {
        return gradeSheet;
    }

    public void setGradeSheet(GradeSheet gradeSheet) {
        this.gradeSheet = gradeSheet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

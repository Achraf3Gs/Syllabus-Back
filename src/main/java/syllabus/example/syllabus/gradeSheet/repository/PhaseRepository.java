package syllabus.example.syllabus.gradeSheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import syllabus.example.syllabus.gradeSheet.entities.Phase;


public interface PhaseRepository extends JpaRepository<Phase, Long> {
}

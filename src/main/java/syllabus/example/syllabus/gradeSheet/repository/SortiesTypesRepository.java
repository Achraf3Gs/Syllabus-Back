package syllabus.example.syllabus.gradeSheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import syllabus.example.syllabus.gradeSheet.entities.Phase;
import syllabus.example.syllabus.gradeSheet.entities.SortiesTypes;

public interface SortiesTypesRepository extends JpaRepository<SortiesTypes, Long>{
}


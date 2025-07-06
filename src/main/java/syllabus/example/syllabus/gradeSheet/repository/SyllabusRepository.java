package syllabus.example.syllabus.gradeSheet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import syllabus.example.syllabus.gradeSheet.entities.FlightDomain;
import syllabus.example.syllabus.gradeSheet.entities.Syllabus;

import java.util.Optional;

@Repository
public interface SyllabusRepository extends CrudRepository<Syllabus, Long> {

    Optional<Syllabus> findByTitle(String title);
}

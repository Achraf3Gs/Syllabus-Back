package syllabus.example.syllabus.gradeSheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import syllabus.example.syllabus.entities.Student;
import syllabus.example.syllabus.gradeSheet.entities.FlightDomain;
import syllabus.example.syllabus.gradeSheet.entities.FlightDomainSyllabus;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightDomainSyllabusRepository extends JpaRepository<FlightDomainSyllabus, Long> {
    List<FlightDomainSyllabus> findBySyllabusId(Long syllabusId);

    Optional<FlightDomainSyllabus> findByName(String name);
}
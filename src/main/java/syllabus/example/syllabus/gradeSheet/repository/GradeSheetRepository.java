package syllabus.example.syllabus.gradeSheet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import syllabus.example.syllabus.gradeSheet.entities.FlightDomainSyllabus;
import syllabus.example.syllabus.gradeSheet.entities.GradeSheet;

import java.util.List;


@Repository
public interface GradeSheetRepository  extends CrudRepository<GradeSheet, Long> {


    List<GradeSheet> findByStudentFlightDomainSyllabusId(Long StudentFlightDomainSyllabusId);
    List<GradeSheet> findByFlightDomainSyllabusId(Long FlightDomainSyllabusId);

}
package syllabus.example.syllabus.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import syllabus.example.syllabus.entities.StudentFlightDomainSyllabus;


import java.util.List;

@Repository
public interface StudentFlightDomainSyllabusRepository extends CrudRepository<StudentFlightDomainSyllabus, Long> {

    List<StudentFlightDomainSyllabus> findByStudentSyllabusId(Long studentSyllabusId);
}

package syllabus.example.syllabus.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import syllabus.example.syllabus.entities.StudentSyllabus;


import java.util.List;
import java.util.Optional;

@Repository
public interface StudentSyllabusRepository extends CrudRepository<StudentSyllabus, Long> {

    Optional<StudentSyllabus> findById(Long id);

    List<StudentSyllabus> findByStudentId(Long studentId);
}

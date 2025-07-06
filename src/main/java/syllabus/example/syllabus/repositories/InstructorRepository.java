package syllabus.example.syllabus.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import syllabus.example.syllabus.entities.Instructor;

import java.util.Optional;

@Repository
public interface InstructorRepository extends CrudRepository<syllabus.example.syllabus.entities.Instructor, Long> {
    Optional<Instructor> findByCallSign(Long callSign);
}


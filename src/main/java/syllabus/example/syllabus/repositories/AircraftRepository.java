package syllabus.example.syllabus.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import syllabus.example.syllabus.entities.Aircraft;






@Repository
public interface AircraftRepository extends CrudRepository<Aircraft, Long> {

}
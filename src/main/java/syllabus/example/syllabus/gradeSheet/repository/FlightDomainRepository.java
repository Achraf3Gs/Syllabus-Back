package syllabus.example.syllabus.gradeSheet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import syllabus.example.syllabus.gradeSheet.entities.FlightDomain;

import java.util.Optional;


@Repository
public interface FlightDomainRepository extends CrudRepository<FlightDomain, Long> {

    Optional<FlightDomain> findByName(String name);
}
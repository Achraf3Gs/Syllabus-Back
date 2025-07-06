package syllabus.example.syllabus.gradeSheet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import syllabus.example.syllabus.gradeSheet.entities.ManeuverItem;





    @Repository
    public interface ManeuverItemRepository extends CrudRepository<ManeuverItem, Long> {


    }

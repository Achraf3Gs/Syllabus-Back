package syllabus.example.syllabus.gradeSheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import syllabus.example.syllabus.gradeSheet.entities.GradeSheet;
import syllabus.example.syllabus.gradeSheet.entities.GradeSheetManeuverItem;

import java.util.List;

public interface GradeSheetManeuverItemRepository extends JpaRepository<GradeSheetManeuverItem, Long> {

    @Query("SELECT gsm FROM GradeSheetManeuverItem gsm " +
            "JOIN FETCH gsm.maneuverItem mi " +
            "WHERE gsm.gradeSheet.id = :gradeSheetId")
    List<GradeSheetManeuverItem> findFullWithManeuverByGradeSheetId(@Param("gradeSheetId") Long gradeSheetId);


    // ✅ Custom delete method
    void deleteByGradeSheet(GradeSheet gradeSheet);
}

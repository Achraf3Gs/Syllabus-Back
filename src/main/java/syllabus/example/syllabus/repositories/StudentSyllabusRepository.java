package syllabus.example.syllabus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import syllabus.example.syllabus.entities.StudentSyllabus;


import java.util.List;
import java.util.Optional;

@Repository
public interface StudentSyllabusRepository extends JpaRepository<StudentSyllabus, Long> {

    boolean existsByStudentIdAndSyllabusId(Long studentId, Long syllabusId);

    @Query("SELECT ss FROM StudentSyllabus ss " +
            "JOIN FETCH ss.student s " +
            "JOIN FETCH ss.syllabus sy " +
            "WHERE ss.id = :id")
    Optional<StudentSyllabus> findByIdWithDetails(@Param("id") Long id);

    // Simple version (lazy loading)
    List<StudentSyllabus> findByStudentId(Long studentId);

    // ✅ Custom fetch version
    @Query("SELECT ss FROM StudentSyllabus ss " +
            "JOIN FETCH ss.student s " +
            "JOIN FETCH ss.syllabus sy " +
            "WHERE s.id = :studentId")
    List<StudentSyllabus> findByStudentIdWithDetails(@Param("studentId") Long studentId);
}


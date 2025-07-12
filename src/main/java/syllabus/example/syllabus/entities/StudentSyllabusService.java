package syllabus.example.syllabus.entities;

import org.springframework.transaction.annotation.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import syllabus.example.syllabus.exceptions.ResourceNotFoundException;
import syllabus.example.syllabus.gradeSheet.entities.Syllabus;
import syllabus.example.syllabus.gradeSheet.repository.SyllabusRepository;
import syllabus.example.syllabus.repositories.StudentRepository;
import syllabus.example.syllabus.repositories.StudentSyllabusRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentSyllabusService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SyllabusRepository syllabusRepository;

    @Autowired
    private StudentSyllabusRepository studentSyllabusRepository;

    @Transactional
    public StudentSyllabus assignSyllabus(Long studentId, Long syllabusId) throws BadRequestException {
        // Find student and syllabus
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        Syllabus syllabus = syllabusRepository.findById(syllabusId)
                .orElseThrow(() -> new ResourceNotFoundException("Syllabus not found with id: " + syllabusId));

        // Check if this syllabus is already assigned to the student
        if (studentSyllabusRepository.existsByStudentIdAndSyllabusId(studentId, syllabusId)) {
            throw new BadRequestException("This syllabus is already assigned to the student");
        }

        // Create and save the assignment
        StudentSyllabus assignment = new StudentSyllabus();
        assignment.setStudent(student);
        assignment.setSyllabus(syllabus);
        assignment.setAssignedDate(LocalDateTime.now());
        assignment.setStatus(StudentSyllabus.Status.ACTIVE);

        return studentSyllabusRepository.save(assignment);
    }

    @Transactional(readOnly = true)
    public List<StudentSyllabusDTO> getSyllabiForStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with id: " + studentId);
        }

        return studentSyllabusRepository.findByStudentIdWithDetails(studentId)
                .stream()
                .map(ss -> new StudentSyllabusDTO(
                        ss.getId(),
                        ss.getSyllabus().getId(),
                        ss.getSyllabus().getTitle(),
                        ss.getAssignedDate(),
                        ss.getStatus().toString()
                ))
                .toList();
    }


}
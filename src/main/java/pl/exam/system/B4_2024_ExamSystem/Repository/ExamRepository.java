package pl.exam.system.B4_2024_ExamSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.exam.system.B4_2024_ExamSystem.Model.Exam;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findExamsByCreatorId(Long userId);

}

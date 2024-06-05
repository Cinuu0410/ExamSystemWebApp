package pl.exam.system.B4_2024_ExamSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.exam.system.B4_2024_ExamSystem.Model.ExamResult;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
}

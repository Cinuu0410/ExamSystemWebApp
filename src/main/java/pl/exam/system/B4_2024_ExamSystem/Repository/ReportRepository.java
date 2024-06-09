package pl.exam.system.B4_2024_ExamSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.exam.system.B4_2024_ExamSystem.Model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}

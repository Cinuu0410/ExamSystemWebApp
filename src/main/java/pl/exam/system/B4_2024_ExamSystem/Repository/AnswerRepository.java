package pl.exam.system.B4_2024_ExamSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.exam.system.B4_2024_ExamSystem.Model.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>{
    List<Answer> findByQuestionId(Long questionId);
}

package pl.exam.system.B4_2024_ExamSystem.Service;

import org.springframework.stereotype.Service;
import pl.exam.system.B4_2024_ExamSystem.Model.Answer;
import pl.exam.system.B4_2024_ExamSystem.Repository.AnswerRepository;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }
}

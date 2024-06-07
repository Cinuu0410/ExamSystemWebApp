package pl.exam.system.B4_2024_ExamSystem.Service;

import org.springframework.stereotype.Service;
import pl.exam.system.B4_2024_ExamSystem.Model.Answer;
import pl.exam.system.B4_2024_ExamSystem.Model.Question;
import pl.exam.system.B4_2024_ExamSystem.Repository.AnswerRepository;
import pl.exam.system.B4_2024_ExamSystem.Repository.QuestionRepository;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    // Metoda do znalezienia poprawnej odpowiedzi dla danego pytania
    public Long getCorrectAnswerId(Question question) {
        List<Answer> answers = question.getAnswers();
        for (Answer answer : answers) {
            if (answer.isCorrect()) {
                return answer.getId();
            }
        }
        return null; // Jeśli nie znaleziono poprawnej odpowiedzi
    }
}

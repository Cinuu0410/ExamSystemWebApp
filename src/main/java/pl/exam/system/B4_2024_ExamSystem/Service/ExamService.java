package pl.exam.system.B4_2024_ExamSystem.Service;

import org.springframework.stereotype.Service;
import pl.exam.system.B4_2024_ExamSystem.Model.*;
import pl.exam.system.B4_2024_ExamSystem.Repository.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final ExamResultRepository examResultRepository;
    private final AnswerRepository answerRepository;

    public ExamService(ExamRepository examRepository, UserRepository userRepository, QuestionRepository questionRepository, ExamResultRepository examResultRepository, AnswerRepository answerRepository) {
        this.examRepository = examRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.examResultRepository = examResultRepository;
        this.answerRepository = answerRepository;
    }

    //Metoda do pobierania egzaminów stworzonych przez egzaminatora
    public List<Exam> getExamsForCreator(Long userId) {
        return examRepository.findExamsByCreatorId(userId);
    }

    //Metoda do dodawania nowego egzaminu
    public void addExam(Exam exam) {
        examRepository.save(exam);
    }

    //Metoda do pobierania wszystkich osób z rolą egzaminowany
    public List<User> getExaminedUsers() {
        System.out.println(userRepository.findAllByRole("Egzaminowany"));
        return userRepository.findAllByRole("Egzaminowany");
    }

    public List<Exam> getExamsForUser(User user) {
        List<Exam> allExams = examRepository.findAll();
        return allExams.stream()
                .filter(exam -> exam.getExaminedUserIds().contains(user.getId()))
                .collect(Collectors.toList());
    }


    public List<Question> getQuestionsForExam(Long examId) {
        System.out.println("Service Pytania: " + questionRepository.findByExamId(examId));
        return questionRepository.findByExamId(examId);
    }

    // Metoda do zapisywania wyniku egzaminu
    public void saveExamResult(Long userId, Long examId, int score) {
        ExamResult examResult = new ExamResult();
        examResult.setUserId(userId);
        examResult.setExamId(examId);
        examResult.setScore(score);

        examResultRepository.save(examResult);
    }


    public int calculateScore(Map<String, String> params) {
        int totalQuestions = 0;
        int correctAnswers = 0;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.startsWith("question_")) {
                totalQuestions++;

                Long questionId = Long.parseLong(key.replace("question_", ""));
                if (isAnswerCorrect(questionId, value)) {
                    correctAnswers++;
                }
            }
        }

        System.out.println("Poprawne odpowiedzi: " + correctAnswers);
        System.out.println("Ilość pytań: " + totalQuestions);

        if (totalQuestions == 0) {
            return 0;
        }

        return (int) ((correctAnswers / (double) totalQuestions) * 100);
    }

    public boolean isAnswerCorrect(Long questionId, String answerId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        if (question != null) {
            for (Answer answer : question.getAnswers()) {
                if (answer.getId().equals(Long.parseLong(answerId)) && answer.isCorrect()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void saveExam(Exam exam) {
        examRepository.save(exam);
    }

    public Exam getExamById(Long examId) {
        return examRepository.findById(examId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono egzaminu o podanym identyfikatorze: " + examId));
    }

    public void deleteExamById(Long examId) {
        examRepository.deleteById(examId);
    }


    public void updateQuestionsAndAnswers(Exam existingExam, Exam editedExam) {
        System.out.println("Starting to update questions and answers for exam: " + existingExam.getId());

        for (Question editedQuestion : editedExam.getQuestions()) {
            System.out.println("Processing question: " + editedQuestion.getId() + " - " + editedQuestion.getQuestion());

            if (editedQuestion.getQuestion() == null || editedQuestion.getQuestion().isEmpty()) {
                System.out.println("Skipping question with null or empty text");
                continue;
            }

            if (editedQuestion.getQuestionType() == null || editedQuestion.getQuestionType().isEmpty()) {
                System.out.println("Skipping question with null or empty type");
                continue;
            }

            Question existingQuestion = questionRepository.findById(editedQuestion.getId()).orElse(new Question());
            System.out.println("Existing question: " + (existingQuestion.getId() != null ? existingQuestion.getId() : "new"));

            existingQuestion.setQuestion(editedQuestion.getQuestion());
            existingQuestion.setQuestionType(editedQuestion.getQuestionType());
            existingQuestion.setExamId(existingExam.getId());

            for (Answer editedAnswer : editedQuestion.getAnswers()) {
                System.out.println("Processing answer: " + editedAnswer.getId() + " - " + editedAnswer.getAnswerText());

                if (editedAnswer.getAnswerText() == null || editedAnswer.getAnswerText().isEmpty()) {
                    System.out.println("Skipping answer with null or empty text");
                    continue;
                }

                Answer existingAnswer = answerRepository.findById(editedAnswer.getId()).orElse(new Answer());
                System.out.println("Existing answer: " + (existingAnswer.getId() != null ? existingAnswer.getId() : "new"));

                existingAnswer.setAnswerText(editedAnswer.getAnswerText());
                existingAnswer.setCorrect(editedAnswer.isCorrect());
                existingAnswer.setQuestion(existingQuestion);
                answerRepository.save(existingAnswer);
            }
            questionRepository.save(existingQuestion);
        }
    }
}

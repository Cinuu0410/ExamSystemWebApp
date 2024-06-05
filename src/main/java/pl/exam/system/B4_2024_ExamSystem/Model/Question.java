package pl.exam.system.B4_2024_ExamSystem.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "exam_id")
    private Long examId;

    @Column(name = "question_text")
    private String question;

    @Column(name = "question_type")
    private String questionType;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    @Override
    public String toString() {
        return "Question{" +
                "id=" + Id +
                ", examId=" + examId +
                ", question='" + question + '\'' +
                ", questionType='" + questionType + '\'' +
                '}';
    }
}

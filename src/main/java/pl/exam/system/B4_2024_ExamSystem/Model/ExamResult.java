package pl.exam.system.B4_2024_ExamSystem.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "exam_result")
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "exam_id")
    private Long examId;

    @Column(name = "score")
    private int score;
}

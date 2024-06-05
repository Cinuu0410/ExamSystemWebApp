package pl.exam.system.B4_2024_ExamSystem.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "duration")
    private int duration;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "examined_users")
    private String examinedUsers;

    @Column(name = "is_active")
    private boolean isActive;

    public void setExaminedUserIds(List<Long> userIds) {
        this.examinedUsers = userIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public List<Long> getExaminedUserIds() {
        if (this.examinedUsers != null && !this.examinedUsers.isEmpty()) {
            return Arrays.stream(this.examinedUsers.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public Exam() {}
}

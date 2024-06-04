package pl.exam.system.B4_2024_ExamSystem.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    public User() {}

    @Transient
    private String rawPassword;
}

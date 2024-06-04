package pl.exam.system.B4_2024_ExamSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.exam.system.B4_2024_ExamSystem.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.role FROM User u WHERE u.Id = :userId")
    String findRole(@Param("userId") Long userId);

    User findByUsername(String username);
}

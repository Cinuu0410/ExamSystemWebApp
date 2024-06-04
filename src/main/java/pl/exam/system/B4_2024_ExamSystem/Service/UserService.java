package pl.exam.system.B4_2024_ExamSystem.Service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.exam.system.B4_2024_ExamSystem.Model.User;
import pl.exam.system.B4_2024_ExamSystem.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String getRole(Long userId){
        return userRepository.findRole(userId);
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            String hashedPassword = hashPassword(password);
            return hashedPassword.equals(user.getPassword());
        }
        return false;
    }

    private String hashPassword(String password) {
        if (password != null) {
            return DigestUtils.sha256Hex(password);
        } else {
            throw new IllegalArgumentException("Password cannot be null");
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

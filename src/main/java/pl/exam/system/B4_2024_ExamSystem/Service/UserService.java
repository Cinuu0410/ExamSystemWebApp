package pl.exam.system.B4_2024_ExamSystem.Service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.exam.system.B4_2024_ExamSystem.Enum.UserRole;
import pl.exam.system.B4_2024_ExamSystem.Model.User;
import pl.exam.system.B4_2024_ExamSystem.Repository.UserRepository;

import java.util.List;

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

    public String hashAndSaltPassword(String password) {
        if (password != null) {
            return hashPassword(password);
        } else {
            throw new IllegalArgumentException("Password cannot be null");
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

//    public void register(String username, String password, String firstName, String lastName, String email, UserRole role) {
//        User newUser = new User();
//        newUser.setUsername(username);
//        String hashedPassword = hashPassword(password);
//        newUser.setPassword(hashedPassword);
//        newUser.setFirstName(firstName);
//        newUser.setLastName(lastName);
//        newUser.setEmail(email);
//        newUser.setRole(role.getRoleName());
//        userRepository.save(newUser);
//    }

    public void register(String username, String password, String firstName, String lastName, String email, boolean role) {
        User newUser = new User();
        newUser.setUsername(username);
        String hashedPassword = hashPassword(password);
        newUser.setPassword(hashedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        if (role) { // Można również użyć 'if (teacher == true)' - to to samo
            newUser.setRole("Egzaminowany");
        } else {
            newUser.setRole("Egzaminator"); // Domyślnie ustawiaj rolę na "USER"
        }
        userRepository.save(newUser);
    }


}

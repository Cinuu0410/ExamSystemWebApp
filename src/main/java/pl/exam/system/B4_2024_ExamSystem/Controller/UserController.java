package pl.exam.system.B4_2024_ExamSystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.exam.system.B4_2024_ExamSystem.Enum.UserRole;
import pl.exam.system.B4_2024_ExamSystem.Model.User;
import pl.exam.system.B4_2024_ExamSystem.Service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public String addUser(@RequestParam String username, @RequestParam String password,
                          @RequestParam String firstName, @RequestParam String lastName,
                          @RequestParam String email, @RequestParam UserRole role,
                          RedirectAttributes redirectAttributes) {
        if (userService.userExists(username)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Użytkownik o podanej nazwie już istnieje.");
            return "redirect:/register";
        }

        String hashedPassword = userService.hashAndSaltPassword(password);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setRole(role.getRoleName());

        userService.saveUser(newUser);
        redirectAttributes.addFlashAttribute("successMessage", "Pomyślnie dodano użytkownika!");
        return "redirect:/admin_panel";
    }

    @PostMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin_panel";
    }

}

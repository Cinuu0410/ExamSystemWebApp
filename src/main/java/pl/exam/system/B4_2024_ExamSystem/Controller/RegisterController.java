package pl.exam.system.B4_2024_ExamSystem.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.exam.system.B4_2024_ExamSystem.Enum.UserRole;
import pl.exam.system.B4_2024_ExamSystem.Service.UserService;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(){
        return "register_page";
    }

//    @PostMapping("/register")
//    public String register(@RequestParam String username, @RequestParam String password,
//                           @RequestParam String firstName, @RequestParam String lastName,
//                           @RequestParam String email, @RequestParam UserRole role,
//                           RedirectAttributes redirectAttributes,
//                           HttpSession session) {
//        if (userService.userExists(username)) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Użytkownik o podanej nazwie już istnieje.");
//            return "redirect:/register";
//        }
//
//        userService.register(username, password, firstName, lastName, email, role);
//
//        session.setAttribute("username", username);
//        redirectAttributes.addFlashAttribute("successMessage", "Rejestracja udana!");
//        return "redirect:/main_page";
//    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password,
                           @RequestParam String firstName, @RequestParam String lastName,
                           @RequestParam String email, @RequestParam(required = false) boolean role, RedirectAttributes redirectAttributes,
                           HttpSession session) {

        if (userService.userExists(username)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Użytkownik o podanej nazwie już istnieje.");
            return "redirect:/register";
        }

        if (userService != null) {
            userService.register(username, password, firstName, lastName, email, role);
        }
        // Zaloguj nowo zarejestrowanego użytkownika
        session.setAttribute("username", username);

        redirectAttributes.addFlashAttribute("successMessage", "Rejestracja udana!");
        return "redirect:/login";
    }
}

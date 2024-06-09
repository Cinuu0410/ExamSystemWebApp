package pl.exam.system.B4_2024_ExamSystem.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.exam.system.B4_2024_ExamSystem.Model.Exam;
import pl.exam.system.B4_2024_ExamSystem.Model.User;
import pl.exam.system.B4_2024_ExamSystem.Service.ExamService;
import pl.exam.system.B4_2024_ExamSystem.Service.UserService;
import pl.exam.system.B4_2024_ExamSystem.Enum.UserRole;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ExamSystemController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExamService examService;


    @GetMapping("/main_page")
    public String mainPage(Model model, HttpSession session, RedirectAttributes redirectAttributes){
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            Long userId = loggedInUser.getId();
            String loggedRole = userService.getRole(userId);

            session.setAttribute("loggedInUser", loggedInUser);
            session.setAttribute("role", loggedRole);
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("role", loggedRole);
        }
        return "main_page";
    }

    @GetMapping("/admin_panel")
    public String adminPanel(Model model, HttpSession session, RedirectAttributes redirectAttributes){
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            Long userId = loggedInUser.getId();
            String loggedRole = userService.getRole(userId);

            if (!loggedRole.equals(UserRole.ADMINISTRATOR.getRoleName())) {
                return "redirect:/main_page";
            }

            session.setAttribute("loggedInUser", loggedInUser);
            session.setAttribute("role", loggedRole);
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("role", loggedRole);

            List<User> getAllUsers = userService.getAllUsers();
            model.addAttribute("getAllUsers", getAllUsers);
        } else {
            return "redirect:/login";
        }
        return "admin_panel_page";
    }

    @GetMapping("/contact_us")
    public String contactUsPage(Model model, HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            Long userId = loggedInUser.getId();
            String loggedRole = userService.getRole(userId);
            session.setAttribute("loggedInUser", loggedInUser);
            session.setAttribute("role", loggedRole);
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("role", loggedRole);
        }
        return "contact_us_page";
    }

    @GetMapping("/about_us")
    public String aboutUsPage(Model model, HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            Long userId = loggedInUser.getId();
            String loggedRole = userService.getRole(userId);
            session.setAttribute("loggedInUser", loggedInUser);
            session.setAttribute("role", loggedRole);
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("role", loggedRole);
        }
        return "about_us_page";
    }

    @GetMapping("/exams")
    public String examsPage(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            Long userId = loggedInUser.getId();
            String loggedRole = userService.getRole(userId);
            session.setAttribute("loggedInUser", loggedInUser);
            session.setAttribute("role", loggedRole);

            List<Exam> exams = examService.getExamsForCreator(userId);
            model.addAttribute("examinedUsers", examService.getExaminedUsers());
            System.out.println(exams);
            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("role", loggedRole);
            model.addAttribute("exams", exams);
        } else {
            return "redirect:/login";
        }
        return "exams_page";
    }

    @GetMapping("/available_exams")
    public String availableExamsPage(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            Long userId = loggedInUser.getId();
            String loggedRole = userService.getRole(userId);
            List<Exam> availableExams = examService.getExamsForUser(loggedInUser);

            session.setAttribute("loggedInUser", loggedInUser);
            session.setAttribute("role", loggedRole);

            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("role", loggedRole);
            model.addAttribute("exams", availableExams);
        }else{
            return "redirect:/login";
        }
        return "available_exams_page";
    }


}

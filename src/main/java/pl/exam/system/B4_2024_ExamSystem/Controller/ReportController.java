package pl.exam.system.B4_2024_ExamSystem.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.exam.system.B4_2024_ExamSystem.Model.Exam;
import pl.exam.system.B4_2024_ExamSystem.Model.Report;
import pl.exam.system.B4_2024_ExamSystem.Model.User;
import pl.exam.system.B4_2024_ExamSystem.Service.ReportService;
import pl.exam.system.B4_2024_ExamSystem.Service.UserService;

import java.util.List;

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @PostMapping("/submitForm")
    public String submitForm(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("sub") String subject,
            @RequestParam("message") String message,
            Model model) {

        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("phone", phone);
        model.addAttribute("subject", subject);
        model.addAttribute("message", message);


        // Tworzenie nowego zgłoszenia
        Report report = new Report();
        report.setName(name);
        report.setEmail(email);
        report.setPhone(phone);
        report.setSubject(subject);
        report.setDescription(message);

        // Zapisywanie zgłoszenia do bazy danych
        reportService.saveReport(report);

        return "confirmation_page";
    }

    @PostMapping("/markAsResolved/{id}")
    public String markAsResolved(@PathVariable("id") Long id) {
        reportService.markReportAsResolved(id);
        return "redirect:/reports";
    }

    @GetMapping("/reports")
    public String getContactUsPage(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            Long userId = loggedInUser.getId();
            String loggedRole = userService.getRole(userId);
            List<Report> reports = reportService.getAllReports();

            session.setAttribute("loggedInUser", loggedInUser);
            session.setAttribute("role", loggedRole);

            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("role", loggedRole);
            model.addAttribute("reports", reports);
        }else{
            return "redirect:/login";
        }
        return "reports_page";
    }

}

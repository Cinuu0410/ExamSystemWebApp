package pl.exam.system.B4_2024_ExamSystem.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.exam.system.B4_2024_ExamSystem.Model.Answer;
import pl.exam.system.B4_2024_ExamSystem.Model.Exam;
import pl.exam.system.B4_2024_ExamSystem.Model.Question;
import pl.exam.system.B4_2024_ExamSystem.Model.User;
import pl.exam.system.B4_2024_ExamSystem.Service.AnswerService;
import pl.exam.system.B4_2024_ExamSystem.Service.ExamService;
import pl.exam.system.B4_2024_ExamSystem.Service.QuestionService;
import pl.exam.system.B4_2024_ExamSystem.Service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;


    @PostMapping("/exams/add")
    public String addExam(@ModelAttribute("exam") Exam exam, HttpSession session, @RequestParam List<Long> examinedUserIds) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        exam.setCreatorId(loggedInUser.getId());
        exam.setExaminedUserIds(examinedUserIds);
        examService.addExam(exam);
        return "redirect:/exams";
    }

    // Metoda do obsługi usuwania egzaminu
    @PostMapping("/delete/{id}")
    public String deleteExam(@PathVariable("id") Long id) {
        examService.deleteExamById(id);
        return "redirect:/exams";
    }

    // Metoda do wyświetlania formularza edycji egzaminu
    @GetMapping("/edit/{examId}")
    public String showEditForm(@PathVariable Long examId, Model model) {
        Exam exam = examService.getExamById(examId);
        System.out.println("Exam ID: " + exam.getId());

        List<Question> questions = examService.getQuestionsForExam(examId);
        System.out.println("Pytania w edycji: " + questions);

        model.addAttribute("exam", exam);
        model.addAttribute("questions", questions);

        return "edit_exam_form_page";
    }

    // Metoda do obsługi zapisu edytowanego egzaminu
    @PostMapping("/edit/{examId}")
    public String editExam(HttpSession session, @PathVariable("examId") Long examId, @ModelAttribute("exam") Exam editedExam) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        Exam existingExam = examService.getExamById(examId);

        editedExam.setId(examId);
        editedExam.setCreatorId(loggedInUser.getId());
        editedExam.setExaminedUsers(existingExam.getExaminedUsers());


        examService.saveExam(editedExam);

        return "redirect:/exams";
    }

    //Ropoczęcie egzaminu przez egzaminatora
    @PostMapping("/start_exam")
    public String startExam(@RequestParam("examId") Long examId) {
        Exam exam = examService.getExamById(examId);

        exam.setActive(true);

        examService.saveExam(exam);

        return "redirect:/exams";
    }

    //Rozpoczęcie egzaminu przez egzaminowanego
    @GetMapping("/start_exam/{examId}")
    public String startExam(@PathVariable Long examId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            Long userId = loggedInUser.getId();
            String loggedRole = userService.getRole(userId);
            session.setAttribute("loggedInUser", loggedInUser);
            session.setAttribute("role", loggedRole);
            List<Question> questions = examService.getQuestionsForExam(examId);

            System.out.println("ExamId: " + examId);
            System.out.println("Pytania: " + questions);

            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("role", loggedRole);
            model.addAttribute("questions", questions);
            model.addAttribute("examId", examId);
        }else{
            return "redirect:/login";
        }
        return "exam_questions_page";
    }


    @PostMapping("/submit_exam/{examId}")
    public String submitExam(Model model, HttpSession session, @PathVariable Long examId, @RequestParam Map<String, String> params) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            Long userId = loggedInUser.getId();
            String loggedRole = userService.getRole(userId);
            session.setAttribute("loggedInUser", loggedInUser);
            session.setAttribute("role", loggedRole);

            model.addAttribute("loggedInUser", loggedInUser);
            model.addAttribute("role", loggedRole);

            int score = examService.calculateScore(params);

            model.addAttribute("examResult", score);
            examService.saveExamResult(userId, examId, score);
        }else{
            return "redirect:/login";
        }
        return "exam_submit_success_page";
    }
}

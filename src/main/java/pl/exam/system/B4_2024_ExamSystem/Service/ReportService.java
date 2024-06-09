package pl.exam.system.B4_2024_ExamSystem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.exam.system.B4_2024_ExamSystem.Model.Report;
import pl.exam.system.B4_2024_ExamSystem.Repository.ReportRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public void saveReport(Report report) {
        report.setIsResolved("false"); // Ustawienie isResolved na false
        reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public void markReportAsResolved(Long id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            report.setIsResolved("true");
            reportRepository.save(report);
        } else {
            System.out.println("problem");
        }
    }
}

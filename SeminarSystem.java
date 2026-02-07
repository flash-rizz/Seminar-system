import javax.swing.*;
import java.util.ArrayList;

public class SeminarSystem {
    
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Session> sessions = new ArrayList<>();
    public static ArrayList<Evaluation> evaluations = new ArrayList<>();
    public static ArrayList<Award> awards = new ArrayList<>();
    public static ArrayList<Report> reports = new ArrayList<>();
    

    public static void main(String[] args) {

        Student student1 = new Student("student1", "123");
        Student student2 = new Student("student2", "123");
        Evaluator eval1 = new Evaluator("lect1", "123");
        Evaluator eval2 = new Evaluator("lect2", "123");
        Coordinator admin = new Coordinator("admin", "admin");

        users.add(student1);
        users.add(student2);
        users.add(eval1);
        users.add(eval2);
        users.add(admin);

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(2026, java.util.Calendar.FEBRUARY, 8);
        Session s1 = new Session(cal.getTime(), "09:00-10:00", "Auditorium A", "Proposal");
        Session s2 = new Session(cal.getTime(), "10:00-11:00", "Auditorium A", "Proposal");
        Session s3 = new Session(cal.getTime(), "11:00-12:00", "Hall B", "Final");

        s1.setStudent(student1);
        s1.setEvaluator(eval1);
        s2.setStudent(student2);
        s2.setEvaluator(eval1);
        s3.setStudent(student1);
        s3.setEvaluator(eval2);

        sessions.add(s1);
        sessions.add(s2);
        sessions.add(s3);

        Submission sub1 = new Submission(
                student1,
                "AI Scheduling in Seminars",
                "Optimizing seminar scheduling using heuristic search.",
                "Dr. Rahman",
                "Oral",
                "/path/to/slides1.pdf",
                "",
                s1
        );
        Submission sub2 = new Submission(
                student2,
                "Poster: Edge Computing",
                "Latency-aware workload placement on edge devices.",
                "Dr. Lim",
                "Poster",
                "/path/to/poster1.pdf",
                "P-12",
                s2
        );
        Submission sub3 = new Submission(
                student1,
                "Final Results: Seminar Analytics",
                "Dashboarding and analytics for seminar evaluation data.",
                "Dr. Rahman",
                "Oral",
                "/path/to/slides2.pdf",
                "",
                s3
        );

        student1.addSubmission(sub1);
        student2.addSubmission(sub2);
        student1.addSubmission(sub3);

        evaluations.add(new Evaluation(student1, eval1, s1, 8, 7, 8, 9, "Strong structure.", true));
        evaluations.add(new Evaluation(student2, eval1, s2, 7, 8, 7, 8, "Good poster design.", false));
        evaluations.add(new Evaluation(student1, eval2, s3, 9, 9, 8, 9, "Excellent final.", true));

        new LoginScreen();
    }
}

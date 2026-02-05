import javax.swing.*;
import java.util.ArrayList;

public class SeminarSystem {
    
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Session> sessions = new ArrayList<>();
    // Evaluations list is used by Award Nomination (filled by Evaluator module)
    public static ArrayList<Object> evaluations = new ArrayList<>();
    

    public static void main(String[] args) {
     
        Student s1 = new Student("Aiman Zaki", "123");
        Student s2 = new Student("Nur Izzah", "123");
        Student s3 = new Student("Haziq Rahman", "123");

        Evaluator e1 = new Evaluator("Dr Lim Wei", "123");
        Evaluator e2 = new Evaluator("Dr Tan Mei", "123");

        Coordinator c1 = new Coordinator("Pn Farah", "admin");

        users.add(s1);
        users.add(s2);
        users.add(s3);
        users.add(e1);
        users.add(e2);
        users.add(c1);

        Session sess1 = new Session(new java.util.Date(), "Lab 1", "Proposal");
        sess1.setStudent(s1);
        sess1.setEvaluator(e1);
        sessions.add(sess1);

        Session sess2 = new Session(new java.util.Date(), "Seminar Room", "Progress");
        sess2.setStudent(s2);
        sess2.setEvaluator(e2);
        sessions.add(sess2);

        evaluations.add(new Evaluation(s1, e1, 8, 7, 9, "Good clarity and results."));
        evaluations.add(new Evaluation(s2, e2, 6, 8, 7, "Solid methodology."));

        new LoginScreen();
    }
}

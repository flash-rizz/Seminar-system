import javax.swing.*;
import java.util.ArrayList;

public class SeminarSystem {
    
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Session> sessions = new ArrayList<>();
    public static ArrayList<Evaluation> evaluations = new ArrayList<>();
    public static ArrayList<Award> awards = new ArrayList<>();
    public static ArrayList<Report> reports = new ArrayList<>();

    public static void initializeUsers() {
        if (!users.isEmpty()) {
            return;
        }

        users.add(new Student("student1", "123"));
        users.add(new Student("student2", "123"));
        users.add(new Evaluator("lect1", "123"));
        users.add(new Evaluator("lect2", "123"));
        users.add(new Coordinator("admin", "admin"));
    }

    public static void main(String[] args) {
        initializeUsers();
        new LoginScreen();
    }
}

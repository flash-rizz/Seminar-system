import javax.swing.*;
import java.util.ArrayList;

public class SeminarSystem {
    
    public static ArrayList<User> users = new ArrayList<>();
    

    public static void main(String[] args) {
     
        users.add(new Student("student1", "123"));
        users.add(new Evaluator("lecturer1", "123"));
        users.add(new Coordinator("admin", "admin"));

        new LoginScreen();
    }
}
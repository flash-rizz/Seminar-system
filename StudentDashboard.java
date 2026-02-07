import javax.swing.*;
import java.awt.GridLayout;

public class StudentDashboard extends JFrame {
    private Student student;

    public StudentDashboard(Student student) {
        this.student = student;

        setTitle("Student Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton registerBtn = new JButton("Register Presentation");
        registerBtn.addActionListener(e -> new RegistrationForm(student));

        JButton viewBtn = new JButton("View My Submission");
        JButton logoutBtn = new JButton("Logout");

        viewBtn.addActionListener(e -> {
            Submission s = student.getSubmission();

            if (s == null) {
                JOptionPane.showMessageDialog(this, "No submission yet!");
            } else {
                JOptionPane.showMessageDialog(this,
                    "Title: " + s.getTitle() +
                    "\nAbstract: " + s.getAbstractText() +
                    "\nSupervisor: " + s.getSupervisor() +
                    "\nType: " + s.getPresentationType() +
                    "\nFile: " + s.getFilePath()
                );
            }
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginScreen();
        });

        setLayout(new GridLayout(3,1));
        add(registerBtn);
        add(viewBtn);
        add(logoutBtn);

        setVisible(true);
    }
}

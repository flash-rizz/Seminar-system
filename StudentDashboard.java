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

        JButton viewBtn = new JButton("View My Submissions");

        viewBtn.addActionListener(e -> {
            java.util.ArrayList<Submission> submissions = student.getSubmissions();

            if (submissions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No submissions yet!");
                return;
            }

            StringBuilder sb = new StringBuilder("My Submissions:\n");
            int idx = 1;
            for (Submission s : submissions) {
                String boardId = s.getBoardId();
                if (boardId == null || boardId.trim().isEmpty()) {
                    boardId = "N/A";
                }
                String sessionInfo = (s.getSession() != null) ? s.getSession().toString() : "No session assigned";
                sb.append(idx++).append(". ").append(sessionInfo).append("\n");
                sb.append("   Title: ").append(s.getTitle()).append("\n");
                sb.append("   Type: ").append(s.getPresentationType()).append("\n");
                sb.append("   Supervisor: ").append(s.getSupervisor()).append("\n");
                sb.append("   Board ID: ").append(boardId).append("\n");
                sb.append("   File: ").append(s.getFilePath()).append("\n\n");
            }

            JOptionPane.showMessageDialog(this, sb.toString());
        });

        JButton sessionBtn = new JButton("View My Sessions");
        sessionBtn.addActionListener(e -> showAssignedSessions());

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> handleLogout());

        setLayout(new GridLayout(4,1));
        add(registerBtn);
        add(viewBtn);
        add(sessionBtn);
        add(logoutBtn);

        setVisible(true);
    }

    private void showAssignedSessions() {
        java.util.ArrayList<Session> assignedSessions = new java.util.ArrayList<>();
        for (Session s : SeminarSystem.sessions) {
            if (s.getStudent() == student) {
                assignedSessions.add(s);
            }
        }

        if (assignedSessions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No session assigned yet.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Assigned Sessions:\n");
        int idx = 1;
        for (Session s : assignedSessions) {
            sb.append(idx++).append(". ").append(s.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void handleLogout() {
        this.dispose();
        new LoginScreen();
    }
}

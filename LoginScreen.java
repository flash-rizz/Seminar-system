import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame implements ActionListener {
    JTextField userField;
    JPasswordField passField;
    JButton btnLogin;

    public LoginScreen() {
        setTitle("FCI Seminar Management System");
        setSize(420, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));
        JLabel title = new JLabel("Seminar Login", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        JLabel subtitle = new JLabel("Enter your username and password", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        header.add(title, BorderLayout.CENTER);
        header.add(subtitle, BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        center.setLayout(new GridLayout(2, 2, 8, 8));

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        userField = new JTextField();

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passField = new JPasswordField();

        center.add(userLabel);
        center.add(userField);
        center.add(passLabel);
        center.add(passField);
        add(center, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBorder(BorderFactory.createEmptyBorder(5, 20, 15, 20));
        btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(new Dimension(120, 30));
        btnLogin.addActionListener(this);
        footer.add(btnLogin);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userField.getText();
        String password = new String(passField.getPassword());
        User foundUser = null;

        for (User u : SeminarSystem.users) {
            if (u.login(username, password)) {
                foundUser = u;
                break;
            }
        }

        if (foundUser != null) {
            JOptionPane.showMessageDialog(this, "Welcome " + foundUser.getRole() + "!");
            this.dispose(); 
            
            if (foundUser instanceof Student) {

            } else if (foundUser instanceof Coordinator) {
                new CoordinatorDashboard();

            } else if (foundUser instanceof Evaluator) {
                new EvaluatorDashboard((Evaluator) foundUser);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

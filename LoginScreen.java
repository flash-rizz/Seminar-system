import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame implements ActionListener {
    JTextField userField;
    JPasswordField passField;
    JButton btnLogin;

    public LoginScreen() {
        setTitle("FCI Seminar Management System");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window
        setLayout(new GridLayout(4, 1, 10, 10)); 

        JLabel title = new JLabel("Seminar Login", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title);

        JPanel inputs = new JPanel(new GridLayout(2, 2, 5, 5));
        inputs.add(new JLabel("   Username:"));
        userField = new JTextField();
        inputs.add(userField);
        inputs.add(new JLabel("   Password:"));
        passField = new JPasswordField();
        inputs.add(passField);
        add(inputs);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(this);
        add(btnLogin);

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

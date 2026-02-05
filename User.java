// Abstract Parent Class
public abstract class User {
    protected String username;
    protected String password;
    protected String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean login(String inputUser, String inputPass) {
        return username.equals(inputUser) && password.equals(inputPass);
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String toString() { return username; }
}

class Student extends User {
    public Student(String username, String password) {
        super(username, password, "Student");
    }
}

class Evaluator extends User {
    public Evaluator(String username, String password) {
        super(username, password, "Evaluator");
    }
}

public class Student extends User {
    private Submission submission;  // add this for the Student module

    public Student(String username, String password) {
        super(username, password, "Student");
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public Submission getSubmission() {
        return submission;
    }
}
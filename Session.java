import java.text.SimpleDateFormat;
import java.util.Date;

public class Session {
    private Date date;
    private String timeSlot;
    private String venue;
    private String sessionType;
    private Student student;
    private Evaluator evaluator;

    public Session(Date date, String timeSlot, String venue, String sessionType) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.venue = venue;
        this.sessionType = sessionType;
    }

    public Date getDate() { return date; }
    public String getTimeSlot() { return timeSlot; }
    public String getVenue() { return venue; }
    public String getSessionType() { return sessionType; }
    public Student getStudent() { return student; }
    public Evaluator getEvaluator() { return evaluator; }

    public void setStudent(Student student) { this.student = student; }
    public void setEvaluator(Evaluator evaluator) { this.evaluator = evaluator; }

    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
        String dateStr = (date != null) ? fmt.format(date) : "No Date";
        String timeStr = (timeSlot != null && !timeSlot.trim().isEmpty()) ? timeSlot : "No Time";
        String studentStr = (student != null) ? student.getUsername() : "Unassigned";
        String evaluatorStr = (evaluator != null) ? evaluator.getUsername() : "Unassigned";
        return dateStr + " " + timeStr + " | " + sessionType + " | " + venue
                + " | Student: " + studentStr + " | Evaluator: " + evaluatorStr;
    }
}

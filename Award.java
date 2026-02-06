import java.util.Date;

public class Award {
    private String awardName;
    private Student winner;
    private double score;
    private Date awardedAt;

    public Award(String awardName, Student winner, double score) {
        this.awardName = awardName;
        this.winner = winner;
        this.score = score;
        this.awardedAt = new Date();
    }

    public String getAwardName() { return awardName; }
    public Student getWinner() { return winner; }
    public double getScore() { return score; }
    public Date getAwardedAt() { return awardedAt; }

    public String toString() {
        String winnerName = (winner != null) ? winner.getUsername() : "Unknown Student";
        return awardName + " - " + winnerName + " (" + score + ")";
    }
}


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Report {
    private Date generatedDate;
    private String content;

    public Report(String content) {
        this.generatedDate = new Date();
        this.content = content;
    }

    public Date getGeneratedDate() { return generatedDate; }
    public String getContent() { return content; }

    public static Report generate(ArrayList<Session> sessions, ArrayList<Evaluation> evaluations, ArrayList<Award> awards) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFmt = new SimpleDateFormat("dd MMM yyyy HH:mm");

        sb.append("FCI Seminar Final Evaluation Report\n");
        sb.append("Generated: ").append(dateFmt.format(new Date())).append("\n\n");
        sb.append("Total Sessions: ").append(sessions.size()).append("\n");
        sb.append("Total Evaluations: ").append(evaluations.size()).append("\n");
        sb.append("Total Awards: ").append(awards.size()).append("\n\n");

        if (!evaluations.isEmpty()) {
            double claritySum = 0;
            double methodologySum = 0;
            double resultsSum = 0;
            double presentationSum = 0;
            int voteCount = 0;

            for (Evaluation eval : evaluations) {
                claritySum += eval.getProblemClarity();
                methodologySum += eval.getMethodology();
                resultsSum += eval.getResults();
                presentationSum += eval.getPresentation();
                if (eval.isPeoplesChoiceVote()) voteCount++;
            }

            double count = evaluations.size();
            sb.append("Analytics:\n");
            sb.append("- Avg Problem Clarity: ").append(String.format("%.2f", claritySum / count)).append("\n");
            sb.append("- Avg Methodology: ").append(String.format("%.2f", methodologySum / count)).append("\n");
            sb.append("- Avg Results: ").append(String.format("%.2f", resultsSum / count)).append("\n");
            sb.append("- Avg Presentation: ").append(String.format("%.2f", presentationSum / count)).append("\n");
            sb.append("- People's Choice Votes: ").append(voteCount).append("\n\n");
        }

        if (!sessions.isEmpty()) {
            sb.append("Sessions:\n");
            int index = 1;
            for (Session session : sessions) {
                sb.append(index++).append(". ").append(session).append("\n");
            }
            sb.append("\n");
        }

        if (!awards.isEmpty()) {
            sb.append("Awards:\n");
            int index = 1;
            for (Award award : awards) {
                sb.append(index++).append(". ").append(award).append("\n");
            }
            sb.append("\n");
        }

        return new Report(sb.toString());
    }
}

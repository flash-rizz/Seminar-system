import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
            double sumClarity = 0;
            double sumMethodology = 0;
            double sumResults = 0;
            double sumPresentation = 0;
            Evaluation topEval = evaluations.get(0);

            for (Evaluation eval : evaluations) {
                sumClarity += eval.getProblemClarity();
                sumMethodology += eval.getMethodology();
                sumResults += eval.getResults();
                sumPresentation += eval.getPresentation();
                if (eval.getTotalScore() > topEval.getTotalScore()) {
                    topEval = eval;
                }
            }

            double count = evaluations.size();
            sb.append("Analytics:\n");
            sb.append(String.format("Average Problem Clarity: %.2f%n", sumClarity / count));
            sb.append(String.format("Average Methodology: %.2f%n", sumMethodology / count));
            sb.append(String.format("Average Results: %.2f%n", sumResults / count));
            sb.append(String.format("Average Presentation: %.2f%n", sumPresentation / count));
            String topStudent = topEval.getStudent() != null ? topEval.getStudent().getUsername() : "Unknown";
            sb.append("Top Performer: ").append(topStudent)
                    .append(" (").append(topEval.getTotalScore()).append(")\n\n");
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

    public boolean exportAsText(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.print(content);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean exportSummaryCsv(String filePath, ArrayList<Evaluation> evaluations) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("student,evaluator,problemClarity,methodology,results,presentation,total");
            for (Evaluation eval : evaluations) {
                String student = eval.getStudent() != null ? eval.getStudent().getUsername() : "Unknown";
                String evaluator = eval.getEvaluator() != null ? eval.getEvaluator().getUsername() : "Unknown";
                writer.println(student + "," + evaluator + ","
                        + eval.getProblemClarity() + ","
                        + eval.getMethodology() + ","
                        + eval.getResults() + ","
                        + eval.getPresentation() + ","
                        + eval.getTotalScore());
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

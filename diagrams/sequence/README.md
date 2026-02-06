# Sequence Diagrams (PlantUML)

These sequence diagrams are written to match the current Java Swing flow in this repo (and the OOAD lab deliverables asking for multiple sequence diagrams).

Files:
- `01-login.puml` — login and dashboard routing by role
- `02-student-register-upload.puml` — student registers + uploads file (creates `Submission`)
- `03-student-view-submission.puml` — student views their own submission
- `04-coordinator-create-session.puml` — coordinator creates a `Session`
- `05-coordinator-assign-session.puml` — coordinator assigns `Student` / `Evaluator` to a `Session`
- `06-evaluator-submit-evaluation.puml` — evaluator submits an `Evaluation` for a student
- `07-coordinator-award-nomination.puml` — coordinator calculates highest score / top 3 from `Evaluation`
- `08-coordinator-generate-report.puml` — coordinator generates final report from sessions/evaluations/awards

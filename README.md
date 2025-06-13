# BilimApp

This project contains a Spring Boot application. The initial domain model includes several learning-related entities used by the Telegram mini app.

## Entities

- **Subject** – top level course or discipline.
- **SubjectSubgroup** – subsection within a subject.
- **Question** – belongs to a subgroup and stores question text.
- **Answer** – possible answer related to a question.
- **AppUser** – Telegram mini app user information.
- **Region** – administrative area with Kyrgyz and Russian names.
- **City** – belongs to a region.
- **School** – belongs to a city and contains users.

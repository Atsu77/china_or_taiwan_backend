CREATE TABLE IF NOT EXISTS quiz_results (
  id SERIAL PRIMARY KEY,
  quiz_id INT REFERENCES quizzes(id),
  is_correct BOOLEAN NOT NULL
);
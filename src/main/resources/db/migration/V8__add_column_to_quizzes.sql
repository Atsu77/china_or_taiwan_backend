ALTER TABLE quizzes
ADD COLUMN difficulty_id INT REFERENCES difficulties(id) DEFAULT NULL,
ADD COLUMN correct_rate INT DEFAULT 100;

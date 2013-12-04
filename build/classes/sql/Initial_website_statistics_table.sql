USE c_cs108_sqcheng;

-- ======================================================================	
-- Website Statistics (WS)
-- ======================================================================	
DROP TABLE IF EXISTS WS;
-- remove table if it already exists and start from scratch

CREATE TABLE WS (
	Name CHAR(50),
	Number INT
);

INSERT INTO WS VALUES
("QuestionResponseNum", 1),
("PictureResponseNum", 1),
("FillInBlankNum", 1),
("MultipleChoiceNum", 1),
("QuizNum", 2);


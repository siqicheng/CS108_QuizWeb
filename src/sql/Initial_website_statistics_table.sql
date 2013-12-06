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
("QuestionResponseNum", 0),
("PictureResponseNum", 0),
("FillInBlankNum", 0),
("MultipleChoiceNum", 0),
("QuestionResponseMultiAnswerNum",0),
("MultipleChoiceMultipleAnswerNum",0),
("MatchingNum",0),
("QuizNum", 0);


-- ======================================================================	
-- Initailization of Quiz table
-- Two Tables
-- Currently support seven types:
--		
-- ======================================================================	


USE c_cs108_sqcheng;

-- ======================================================================	
-- Quiz Info (QI) table.
-- ======================================================================	
DROP TABLE IF EXISTS QI;
-- remove table if it already exists and start from scratch
CREATE TABLE QI (
	QuizID INT,
	QuizName CHAR(32),
    CreaterId CHAR(32),
    CreateTime TIMESTAMP,
    QuizTag CHAR(255),
    QuizCategory CHAR(32),
    
    canPractice BOOLEAN,
    isRandom BOOLEAN,
    isOnePage BOOLEAN,
    isImmediateCorrection BOOLEAN
);

INSERT INTO QI VALUES
 (0, "FirstQuizEver", "sqcheng", "2013-11-20 04:21:07", "#CommonSense##CS#","CommonSense",false,false,true,false),
 (1, "QuizExample1", "sqcheng", "2013-11-20 04:22:07", "#CommonSense##CS#","CommonSense",false,false,true,false);

 -- ======================================================================	
-- Quiz Questions Mapping (QQ) table. 
-- Note that the least significant digit is the number of question type
--	1	Question Response(QR)
--	2	Fill In The Blank(FB)
--	3	Multiple Choice(MC)
--	4	Picture Response(PR)
--	5	Multiple Answer(MA)
--	6	Multiple Choice Multiple Answer(MCMA)
--	7	Matching Question(MQ) 
-- ======================================================================	
DROP TABLE IF EXISTS QQ;
-- remove table if it already exists and start from scratch

CREATE TABLE QQ (
	QuizID INT,
	QuestionID INT,
	QuestionType INT
);

INSERT INTO QQ VALUES
 (0, 0, 1),
 (0, 0, 2),
 (1, 0, 3),
 (1, 0, 4);
 
 
 
 
 
 
 
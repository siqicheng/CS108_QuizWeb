-- ======================================================================	
-- Initailization of MySQL table
-- Define different types of questions
-- Currently support seven types:
--		Question Response(QR)
--		Fill In The Blank(FB)
--		Multiple Choice(MC)
--		Picture Response(PR)
--		Multiple Answer(MA)
--		Multiple Choice Multiple Answer(MCMA)
--		Matching Question(MQ)
-- ======================================================================	


USE c_cs108_sqcheng;

-- ======================================================================	
-- Question Type One Question Response(QR)
-- ======================================================================	
DROP TABLE IF EXISTS QR;
-- remove table if it already exists and start from scratch
CREATE TABLE QR (
	QuestionID INT,
	CreaterID CHAR(32),
	Question BLOB,
	Answer BLOB,
	Score INT,
	Tag CHAR(32),
    Time INT
);
INSERT INTO QR VALUES
 (0, "Siqi", "What is the teacher's name? (CS108)", "Patrick Young", 
 		 5, "#CommonSense##CS#", 0);

-- ======================================================================	
-- Question Type Two Fill In The Blank(FB)
--  ======================================================================	
DROP TABLE IF EXISTS FB;
-- remove table if it already exists and start from scratch
CREATE TABLE FB (
	QuestionID INT,
	CreaterID CHAR(32),
	Question BLOB,
	Answer BLOB,
	Score INT,
	Tag CHAR(32),
    Time INT
);

INSERT INTO FB VALUES
(0, "Siqi", "#blank# is the CS108's teacher this quarter(2013Autumn).", "Patrick Young", 
		5, "#CommonSense##CS#", 0);

-- ======================================================================	
-- Question Type Three Multiple Choice(MC)
-- ======================================================================	
DROP TABLE IF EXISTS MC;
-- remove table if it already exists and start from scratch
CREATE TABLE MC ( 
	QuestionID INT,
	CreaterID CHAR(32),
	Question BLOB,
	Choices BLOB,
	Answer BLOB,
	Score INT,
	Tag CHAR(32),
    Time INT
);         
         
INSERT INTO MC VALUES
(0, "Siqi", "What is the teacher's name? (CS108)", 
		"#Zhang##Li##Du##Cheng#Patrick Young#","Patrick Young",5, "#CommonSense##CS#", 0);
	 
-- ======================================================================	
-- Question Type Four Picture Response(PR)
-- ======================================================================	
DROP TABLE IF EXISTS PR;
-- remove table if it already exists and start from scratch
CREATE TABLE PR (
	QuestionID INT,
	CreaterID CHAR(32),
	Question BLOB,
	Answer BLOB,
	Url BLOB,
	Score INT,
	Tag CHAR(32),
    Time INT
);         
         
INSERT INTO PR VALUES
(0,"Siqi",  "What is the Acronym of this building ?","MC",
		"http://events.stanford.edu/events/252/25201/Memchu_small.jpg", 5, "#CommonSense##Stanford#", 0);  
		
-- ======================================================================	
-- Question Type Five Multiple Answer(MA)
-- ======================================================================	
DROP TABLE IF EXISTS MA;
-- remove table if it already exists and start from scratch
CREATE TABLE MA (   
	QuestionID INT,
	CreaterID CHAR(32),
	Question BLOB,
	Answer BLOB,
	IsOrder CHAR(8),
	Score INT,
	Tag CHAR(32),
    Time INT
);

INSERT INTO MA VALUES        
(0,"Siqi",  "List the last name of all the team members in this project group?","#Zhang##Li##Du##Cheng#",
		"false", 8, "#CommonSense##Stanford#", 0);  

-- ======================================================================	
-- Question Type Six Multiple Choice Multiple Answer(MCMA)
-- ======================================================================	
DROP TABLE IF EXISTS MCMA;
-- remove table if it already exists and start from scratch
CREATE TABLE MCMA (  
	QuestionID INT,
	CreaterID CHAR(32),
	Question BLOB,
	Choices BLOB,
	Answer BLOB,
	Score INT,
	Tag CHAR(32),
    Time INT    
);
INSERT INTO MCMA VALUES
(0,"Siqi", "What is the last name of all the team members in this project group?(Choose all that is correct)","#Zhang##Li##Du##Cheng#",
		"#Zhang##Li##Du##Cheng#", 8, "#CommonSense##Stanford#", 0); 

-- ======================================================================	
-- Question Type Seven Matching Question (MQ)
-- ======================================================================	
DROP TABLE IF EXISTS MQ;
-- remove table if it already exists and start from scratch
CREATE TABLE MQ (  
	QuestionID INT,
	CreaterID CHAR(32),
	Question BLOB,
	Choices BLOB,
	Answer BLOB,
	Score INT,
	Tag CHAR(32),
    Time INT  
);

INSERT INTO MQ VALUES
(0,"Siqi", "Matching the first name and last name","#Zhang##Li##Du##Cheng#",
		"#Hao##Haoran##Wenxiao##Siqi#", 8, "#CommonSense##Stanford#", 0); 

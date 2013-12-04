USE c_cs108_sqcheng;

-- ======================================================================	
-- QUIZ TAKE HISTORY Table
-- ======================================================================	
DROP TABLE IF EXISTS quiz_take_history;
-- remove table if it already exists and start from scratch

CREATE TABLE quiz_take_history (
	User_Name CHAR(32),
	Quiz_Id INT,
	Score INT,
	Start_Time TIMESTAMP,
	End_Time TIMESTAMP
);

<<<<<<< HEAD
-- INSERT INTO quiz_take_history VALUES
-- ("shrink_du", 0, 200, "2013-12-01 05:00:07", "2013-12-01 05:30:07"),
-- ("shrink_du", 1, 100, "2013-12-01 03:00:07", "2013-12-01 04:00:07"),
-- ("shrink_du", 0, 100, "2013-12-01 04:10:07", "2013-12-01 04:30:07");
=======
INSERT INTO quiz_take_history VALUES
("shrink_du", 0, 200, "2013-12-01 05:00:07", "2013-12-01 05:30:07"),
("shrink_du", 1, 100, "2013-12-01 03:00:07", "2013-12-01 04:00:07"),
("shrink_du", 0, 100, "2013-12-01 04:10:07", "2013-12-01 04:30:07");


-- ======================================================================	
-- Partial Achievement Table
-- ======================================================================	
DROP TABLE IF EXISTS Partial_Achievements;
-- remove table if it already exists and start from scratch

CREATE TABLE Partial_Achievements (
	User_Name CHAR(32),
	Achievement_Type CHAR(50)
);

INSERT INTO Partial_Achievements VALUES ("shrink_du", "I am the Greatest");
>>>>>>> fedff63a389bd4a659551b83b603ae63878ad499

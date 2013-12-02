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

INSERT INTO quiz_take_history VALUES
("shrink_du", 0, 200, "2013-12-01 05:00:07", "2013-12-01 05:30:07"),
("shrink_du", 1, 100, "2013-12-01 03:00:07", "2013-12-01 04:00:07"),
("shrink_du", 0, 100, "2013-12-01 04:10:07", "2013-12-01 04:30:07");
USE c_cs108_sqcheng;
-- ======================================================================	
-- Friend Relationship Request Table
-- ======================================================================	
DROP TABLE IF EXISTS mailTable;
-- remove table if it already exists and start from scratch

CREATE TABLE mailTable (
	sender CHAR(32),
	receiver CHAR(32),
	message BLOB,
	senttime TIMESTAMP,
	hasRead CHAR(32)
);
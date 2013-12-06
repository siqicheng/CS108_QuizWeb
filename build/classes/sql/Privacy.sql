USE c_cs108_sqcheng;
-- ======================================================================	
-- Friend Relationship Request Table
-- ======================================================================	
DROP TABLE IF EXISTS privacyTable;
-- remove table if it already exists and start from scratch

CREATE TABLE privacyTable (
	username CHAR(32),
	isPrivate CHAR(32)
);
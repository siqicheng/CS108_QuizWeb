USE c_cs108_sqcheng;

-- ======================================================================	
-- Friend Relationship Table
-- ======================================================================	
DROP TABLE IF EXISTS friendTable;
-- remove table if it already exists and start from scratch

CREATE TABLE friendTable (
	UserName CHAR(32),
	FriendName CHAR(32)
);

-- ======================================================================	
-- Friend Relationship Request Table
-- ======================================================================	
DROP TABLE IF EXISTS friendRequestTable;
-- remove table if it already exists and start from scratch

CREATE TABLE friendRequestTable (
	UserName CHAR(32),
	FriendName CHAR(32),
	Message BLOB,
	SentTime TIMESTAMP
);
USE c_cs108_sqcheng;

-- ======================================================================	
-- Question Type One Question Response(QR)
-- ======================================================================	
DROP TABLE IF EXISTS userTable;
-- remove table if it already exists and start from scratch

CREATE TABLE userTable (
	UserId INT,
	UserName CHAR(32),
	Password CHAR(40),
	RegistrationTime datetime, 
	Status char(1), -- u/s: u-user;s-admin
	Gender char(1), -- m/f
	Email varchar(50),
	category text;
)

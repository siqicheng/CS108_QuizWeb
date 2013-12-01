USE c_cs108_sqcheng;

-- ======================================================================	
-- Question Type One Question Response(QR)
-- ======================================================================	
DROP TABLE IF EXISTS userTable;
-- remove table if it already exists and start from scratch

CREATE TABLE userTable (
	UserName CHAR(32),
	Password CHAR(40),
	Status CHAR(1), -- u/s: u-user;s-admin
	Gender CHAR(1), -- m/f
	Email CHAR(50)
);

INSERT INTO userTable VALUES
("sqcheng", "4181eecbd7a755d19fdf73887c54837cbecf63fd", "s","m","siqicheng.fdu@gmail.com"),
("shrink_du", "0af4bf3f4e9be29661b3a27243c4458dc4bf1720", "u", "m", "shrink.du@gmail.com");

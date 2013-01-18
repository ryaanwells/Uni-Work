DROP TABLE ATTENDANCE
go
DROP TABLE DOG
go
DROP TABLE BREED
go
DROP TABLE OWNER
go
DROP TABLE KENNEL
go
DROP TABLE SHOW
go
CREATE TABLE BREED(
    breedname varchar(64) PRIMARY KEY
)
go
CREATE TABLE OWNER(
    ownerid INTEGER PRIMARY KEY,
    name varchar(32),
    phone varchar(16) DEFAULT NULL
)
go
CREATE TABLE KENNEL(
    kennelname varchar(64) PRIMARY KEY,
    address varchar(64),
    phone varchar(16) DEFAULT NULL
)
go
CREATE TABLE SHOW(
    showname varchar(64),
    opendate varchar(12),
    closedate varchar(12),
    PRIMARY KEY (showname, opendate)
)
go
CREATE TABLE DOG(
    dogid INTEGER PRIMARY KEY,
    name varchar(32) UNIQUE,
    ownerid INTEGER,
    kennelname varchar(64),
    breedname varchar(64),
    mothername varchar(64) DEFAULT NULL,
    fathername varchar(64) DEFAULT NULL,
    FOREIGN KEY(ownerid) REFERENCES OWNER(ownerid)
)
go
CREATE TABLE ATTENDANCE(
    dogid INTEGER NOT NULL,
    showname varchar(64) NOT NULL,
    opendate varchar(12) NOT NULL,
    place INTEGER DEFAULT NULL,
    PRIMARY KEY (dogid,showname,opendate),
    FOREIGN KEY(showname,opendate) REFERENCES SHOW(showname,opendate)
)
go

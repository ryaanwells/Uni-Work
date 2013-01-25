DROP TABLE MemberOf
go
DROP TABLE Song
go
DROP TABLE Member
go
DROP TABLE Release
go
DROP TABLE Band
go
CREATE TABLE  Band (
    bid INTEGER  PRIMARY KEY,
    name  VARCHAR(25) NOT NULL,
    country  VARCHAR(20),
    webpage   VARCHAR(40)
)
GO
CREATE TABLE Release (
    rid INTEGER PRIMARY KEY,
    bid INTEGER,
    title VARCHAR(50),
    year INTEGER,
    type VARCHAR(10),
    rating INTEGER DEFAULT NULL,
    FOREIGN KEY(bid) REFERENCES Band(bid)
)
GO
CREATE TABLE Song (
    title VARCHAR(100),
    rid INTEGER,
    cdbonus CHAR(1),
    PRIMARY KEY(title,rid),
    FOREIGN KEY(rid) REFERENCES Release(rid)
)
GO
CREATE TABLE Member (
    mid INTEGER PRIMARY KEY,
    name VARCHAR(30),
    stillalive CHAR(1)
)
GO
CREATE TABLE MemberOf (
    mid INTEGER,
    bid INTEGER,
    startyear INTEGER,
    endyear INTEGER,
    instrument VARCHAR(15),
    FOREIGN KEY(mid) REFERENCES Member(mid),
    FOREIGN KEY(bid) REFERENCES Band(bid)
)
GO


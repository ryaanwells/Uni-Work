DROP INDEX membernames
go
DROP INDEX bandnames
go
CREATE INDEX membernames ON Member(NAME)
go
CREATE INDEX bandnames ON Band(NAME)
go
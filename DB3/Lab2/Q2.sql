(SELECT *
FROM MEMBER
WHERE NAME LIKE 'Tim%')
UNION
(SELECT *
FROM MEMBER
WHERE NAME LIKE 'Tom %')
go
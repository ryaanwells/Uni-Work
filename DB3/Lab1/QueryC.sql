SELECT *
    FROM (SELECT DOGID, AVG(PLACE) as AVGP
            FROM ATTENDANCE
                HAVING COUNT(DOGID)>1
                    GROUP BY DOGID)FIRST
          WHERE FIRST.AVGP = 1
        
        
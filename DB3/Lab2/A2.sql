SELECT RESULTS.NAME AS Band_Name, MAX(RESULTS.TOTS) AS Bonus_Num
FROM(
    SELECT BAND.NAME,(0)as TOTS
    FROM BAND
    UNION
    SELECT BAND.NAME, COUNT(SONG.RID)
    FROM BAND, RELEASE, SONG
    WHERE BAND.BID=RELEASE.BID AND RELEASE.RID=SONG.RID AND SONG.CDBONUS='Y'
    GROUP BY BAND.NAME
    )RESULTS
GROUP BY RESULTS.NAME
GO
CREATE INDEX meminst ON MemberOf(Instrument)
go
SELECT BAND.NAME AS Band_Name, MEMBER.NAME AS Singer_Name,
RELEASE.TITLE AS Album_Name, MostProlific.Total AS Song_Num
From BAND, RELEASE, MEMBEROF, MEMBER, 
(
    SELECT AlbumTracks.RID, AlbumTracks.Total
    FROM 
    (
        SELECT RELEASE.RID, COUNT(RELEASE.Title) AS total
        FROM RELEASE, SONG
        WHERE RELEASE.RID=SONG.RID 
        GROUP BY RELEASE.RID
        ORDER BY COUNT(RELEASE.Title) DESC
        ) AlbumTracks 
        INNER JOIN 
        (
        SELECT MAX(COUNT(RELEASE.Title)) AS total
        FROM RELEASE, SONG, MEMBEROF
        WHERE RELEASE.RID=SONG.RID AND RELEASE.BID=MEMBEROF.BID 
        AND 
        (   
             RELEASE.YEAR>=MEMBEROF.STARTYEAR 
             OR 
             MEMBEROF.STARTYEAR IS NULL
        )
        AND 
        (
             RELEASE.YEAR<=MEMBEROF.ENDYEAR 
             OR 
             MEMBEROF.ENDYEAR IS NULL
        ) 
        AND MEMBEROF.INSTRUMENT='vocals'
        GROUP BY RELEASE.RID
        ORDER BY COUNT(RELEASE.TITLE) DESC
    ) MostSongs
    ON AlbumTracks.total=MostSongs.total
) MostProlific
WHERE BAND.BID=Release.BID AND Release.RID=MostProlific.RID 
AND MEMBEROF.INSTRUMENT='vocals' AND MEMBEROF.MID=MEMBER.MID
AND (MEMBEROF.STARTYEAR <= Release.YEAR or MEMBEROF.STARTYEAR IS NULL)
AND (MEMBEROF.ENDYEAR >=RELEASE.YEAR OR MEMBEROF.ENDYEAR IS NULL) 
AND MEMBEROF.BID=BAND.BID
go
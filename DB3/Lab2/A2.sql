SELECT NAME AS Band_Name, Bonus.Bonus_Num AS Bonus_Num
From Band, (SELECT Release.BID, Count(Release.rid)as Bonus_Num
FROM Song, Release
Where song.CDBONUS='Y' and Song.RID=Release.RID
group by Release.BID) Bonus
Where Band.BID = Bonus.BID
ORDER BY BAND.NAME
go
Select Name as Band_Name, Bonus.Bonus_Num
From Band, (SELECT Release.BID, Count(Release.rid)as Bonus_Num
FROM Song, Release
Where song.CDBONUS='Y' and Song.RID=Release.RID
group by Release.BID) Bonus
Where Band.BID = Bonus.BID
go
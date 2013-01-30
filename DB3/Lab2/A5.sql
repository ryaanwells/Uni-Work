(Select *
from MemberOf
where Instrument='vocals')Singers

(Select * from(
Select Release.RID, Count(Release.Title) as total
    From Release, SONG
    Where Release.RID=Song.RID
    group by Release.RID
    order by Count(Release.TItle) DESC)
where rownum=1)topBand


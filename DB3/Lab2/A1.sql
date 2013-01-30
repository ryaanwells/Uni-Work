Select Band.name, Mult.TITLE
From Band, Release, (Select distinct a.rid, a.title
    from Song a, Song b
    where a.RID<>b.RID AND a.title=b.title) Mult
Where Band.BID=Release.BID And Release.RID=Mult.RID
go
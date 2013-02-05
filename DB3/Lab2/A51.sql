
select name, title, total, year
from(
select Band.name as name, Release.title as title, MEMBER.NAME as year, MostProlific.Total as total
From Band, Release,(
    select AlbumTracks.RID, AlbumTracks.Total
    from (
       Select Release.RID, count(Release.title) as total
       From Release, SONG
        Where Release.RID=Song.RID 
        group by Release.RID
        order by Count(Release.TItle) DESC
        ) AlbumTracks 
    inner join (
        Select max(count(r.title)) as total
        From Release r, SONG
        Where r.RID=Song.RID and exists(
            Select *
            From MEMBEROF, Release r
            where r.BID=MEMBEROF.BID and (r.YEAR>=MEMBEROF.STARTYEAR or MEMBEROF.STARTYEAR is null)
            and (r.YEAR<=MEMBEROF.ENDYEAR or MEMBEROF.ENDYEAR is null) and Memberof.INSTRUMENT='vocals'
        ) 
        group by r.RID
        order by Count(r.TItle) DESC
        ) MostSongs
    on AlbumTracks.total=MostSongs.total) MostProlific, MEMBER, MEMBEROF
where Band.BID=Release.BID and Release.RID=MostProlific.RID and MEMBEROF.INSTRUMENT='vocals' 
AND MEMBEROF.MID=MEMBER.MID
AND (MEMBEROF.STARTYEAR <= Release.YEAR or MEMBEROF.STARTYEAR IS NULL)
AND (MEMBEROF.ENDYEAR >=RELEASE.YEAR OR MEMBEROF.ENDYEAR IS NULL) and MEMBEROF.BID=BAND.BID) M
go



Select unique member.name
from Member, MemberOf, Band, Release
where Member.MID=MemberOf.MID and MemberOf.BID=Band.BID 
and MemberOf.instrument='vocals' and Release.year>=MemberOf.STARTYEAR 
and Release.Year<=MemberOf.ENDYEAR
go

select mid
from MemberOf
where MemberOf.INSTRUMENT='vocals'
go


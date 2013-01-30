Select Band.Name as Band_Name, keys.Name as Keyboard_Player
From Band, (Select Member.Name,MemberOf.BID
    from Memberof, Member
    where INSTRUMENT='keyboards' AND Member.MID=MemberOf.MID)keys
Where Band.BID=keys.BID(+)
order by Band.Name, Keys.name
go

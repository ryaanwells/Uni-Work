Select Band.Name as Band_Name, keys.Name as Keyboard_Player
From Band LEFT OUTER JOIN (Select Member.Name,MemberOf.BID
    from Memberof, Member
    where INSTRUMENT='keyboards' AND Member.MID=MemberOf.MID)keys
ON Band.BID=keys.BID
order by Band.Name, Keys.name
go

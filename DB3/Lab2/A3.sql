select Member.NAME as Guitarist_Name, Band.Name as Band_Name, Mems.STARTYEAR as Start_Year, Mems.ENDYEAR as End_Year
From Member, Band, (Select * From MemberOf where INSTRUMENT='guitar')Mems
Where Band.Bid=Mems.Bid AND Member.MID=Mems.MID
ORDER BY Member.NAME, Mems.StartYEar
go

Select *
From MemberOf
Where Instrument='guitar'
go
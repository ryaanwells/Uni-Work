SELECT OWNER.NAME, Count(OWNER.Name)
    FROM OWNER, DOG
       WHERE OWNER.OWNERID = DOG.OWNERID 
            Group by Owner.name
                Having Count(Owner.Name)>10
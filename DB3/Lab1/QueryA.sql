SELECT NAME, DOG.BREEDNAME
    FROM DOG, BREED
        Where DOG.breedname = BREED.breedname
            ORDER BY NAME
go
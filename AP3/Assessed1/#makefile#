all: test

test: finddupl.o mentry.o mlistLL.o 
	gcc -o test finddupl.o mentry.o mlistLL.o

finddupl.o: finddupl.c
	gcc -w -Wall -c finddupl.c

mentry.o: mentry.c
	gcc -w -Wall -c mentry.c

mlistLL.o: mlistLL.c
	gcc -w -Wall -c mlistLL.c